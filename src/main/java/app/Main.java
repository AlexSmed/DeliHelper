package app;

import io.javalin.Javalin;

public class Main {

    public static void main(String[] args) {
        int port = Integer.parseInt(
                System.getenv().getOrDefault("PORT", "7070")
        );

        Javalin app = Javalin.create();

        // Docker health check endpoint
        app.get("/api/auth/healthcheck", ctx -> {
            ctx.status(200).result("OK");
        });

        app.start("0.0.0.0", port);

        System.out.println("DeliHelper API started on port " + port);
    }
}
