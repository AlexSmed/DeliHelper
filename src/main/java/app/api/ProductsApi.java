
package app.api;

import app.dao.ProductsDAO;
import app.entities.Products;
import app.exceptions.ExceptionConfig;
import app.security.JwtMiddleware;
import app.dto.ProductDTO;
import io.javalin.Javalin;

import java.util.Map;

public class ProductsApi {

    private final ProductsDAO productsDAO;

    public ProductsApi(ProductsDAO productsDAO) {
        this.productsDAO = productsDAO;
    }

    public void registerRoutes(Javalin app) {

        app.get("/api/products", ctx -> {
            var products = productsDAO.getAll()
                    .stream()
                    .map(ProductDTO::new)
                    .toList();

            ctx.json(products);
        });

        app.get("/api/products/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));

            Products product = productsDAO.getById(id);

            if (product == null) {
                ctx.status(404);
                ctx.json(new ExceptionConfig.ErrorResponse(
                        "Product not found"
                ));
                return;
            }

            ctx.json(new ProductDTO(product));
        });

        app.post("/api/products", ctx -> {
            if (!JwtMiddleware.isAdmin(ctx)) {
                ctx.status(403).json(
                        Map.of("error", "Administrator access required")
                );
                return;
            }

            Products product = ctx.bodyAsClass(Products.class);

            productsDAO.save(product);

            ctx.status(201);
            ctx.json(new ProductDTO(product));
        });

        app.put("/api/products/{id}", ctx -> {
            if (!JwtMiddleware.isAdmin(ctx)) {
                ctx.status(403).json(
                        Map.of("error", "Administrator access required")
                );
                return;
            }

            Long id = Long.parseLong(ctx.pathParam("id"));

            Products existingProduct = productsDAO.getById(id);

            if (existingProduct == null) {
                ctx.status(404);
                ctx.json(new ExceptionConfig.ErrorResponse(
                        "Product not found"
                ));
                return;
            }

            Products updatedProduct =
                    ctx.bodyAsClass(Products.class);

            existingProduct.setName(updatedProduct.getName());
            existingProduct.setRecipe(updatedProduct.getRecipe());
            existingProduct.setIngredients(updatedProduct.getIngredients());
            existingProduct.setPicture(updatedProduct.getPicture());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setAllergies(updatedProduct.getAllergies());
            existingProduct.setCategory(updatedProduct.getCategory());

            existingProduct.setLastUse(updatedProduct.getLastUse());
            existingProduct.setPlu(updatedProduct.getPlu());

            Products savedProduct =
                    productsDAO.update(existingProduct);

            ctx.json(new ProductDTO(savedProduct));
        });

        app.delete("/api/products/{id}", ctx -> {
            if (!JwtMiddleware.isAdmin(ctx)) {
                ctx.status(403).json(
                        Map.of("error", "Administrator access required")
                );
                return;
            }

            Long id = Long.parseLong(ctx.pathParam("id"));

            Products product = productsDAO.getById(id);

            if (product == null) {
                ctx.status(404);
                ctx.json(new ExceptionConfig.ErrorResponse(
                        "Product not found"
                ));
                return;
            }

            productsDAO.delete(id);

            ctx.status(204);
        });
    }
}
