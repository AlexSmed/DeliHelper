
package app;

import app.api.BulletinApi;
import app.api.ProductsApi;
import app.api.RoutinesApi;
import app.config.HibernateConfig;
import app.dao.BulletinDAO;
import app.dao.ProductsDAO;
import app.dao.RoutinesDAO;
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

        // Health check endpoint
        app.get("/api/auth/healthcheck", ctx -> {
            ctx.status(200).result("OK");
        });

        // Register product routes using the existing database setup
        ProductsDAO productsDAO = new ProductsDAO(emf);
        new ProductsApi(productsDAO).registerRoutes(app);
        RoutinesDAO routinesDAO = new RoutinesDAO(emf);
        new RoutinesApi(routinesDAO).registerRoutes(app);
        BulletinDAO bulletinDAO = new BulletinDAO(emf);
        new BulletinApi(bulletinDAO).registerRoutes(app);

        app.start(port);

        System.out.println("DeliHelper API started on port " + port);
    }
}
