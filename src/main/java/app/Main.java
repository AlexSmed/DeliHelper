package app;

import app.api.ProductsApi;
import app.dao.ProductsDAO;
import io.javalin.Javalin;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("products");

        ProductsDAO productsDAO = new ProductsDAO(emf);

        Javalin app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
        });

        ProductsApi productsApi =
                new ProductsApi(productsDAO);

        productsApi.registerRoutes(app);

        app.start(7000);
    }
}