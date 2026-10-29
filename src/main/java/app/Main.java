package app;

import app.api.BulletinApi;
import app.api.ProductsApi;
import app.api.RoutinesApi;
import app.config.HibernateConfig;
import app.dao.BulletinDAO;
import app.dao.EmployeeDAO;
import app.dao.ProductsDAO;
import app.dao.RoutinesDAO;
import app.security.AuthController;
import app.security.JwtMiddleware;
import app.security.JwtService;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;

public class Main {

    public static void main(String[] args) {
        int port = Integer.parseInt(
                System.getenv().getOrDefault("PORT", "7070")
        );

        EntityManagerFactory emf =
                HibernateConfig.getEntityManagerFactory();

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
        });

        EmployeeDAO employeeDAO = new EmployeeDAO();

        String secret = System.getenv("JWT_SECRET");
        if (secret == null || secret.length() < 32) {
            throw new IllegalStateException(
                    "Configure a strong JWT_SECRET environment variable"
            );
        }

        JwtService jwtService = new JwtService(secret);

        // Authentication
        AuthController authController =
                new AuthController(employeeDAO, jwtService);

        app.post("/api/auth/login", authController::login);

        app.get("/api/auth/healthcheck", ctx -> {
            ctx.status(200).result("OK");
        });

        // Register JWT middleware BEFORE protected API routes
        JwtMiddleware.register(app, jwtService);

        // Product routes
        ProductsDAO productsDAO = new ProductsDAO(emf);
        new ProductsApi(productsDAO).registerRoutes(app);

        // Routine routes
        RoutinesDAO routinesDAO = new RoutinesDAO(emf);
        new RoutinesApi(routinesDAO).registerRoutes(app);

        // Bulletin routes
        BulletinDAO bulletinDAO = new BulletinDAO(emf);
        new BulletinApi(bulletinDAO).registerRoutes(app);

        app.start(port);

        System.out.println(
                "DeliHelper API started on port " + port
        );
    }
}

