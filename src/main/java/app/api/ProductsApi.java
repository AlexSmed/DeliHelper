package app.api;

import app.dao.ProductsDAO;
import app.dto.ProductDTO;
import app.entities.Products;
import io.javalin.Javalin;


public class ProductsApi {


    private final ProductsDAO productsDAO;

    public ProductsApi(ProductsDAO productsDAO) {
        this.productsDAO = productsDAO;
    }

    public void registerRoutes(Javalin app) {

        // GET all products
        app.get("/products", ctx -> {
            var products = productsDAO.getAll()
                    .stream()
                    .map(ProductDTO::new)
                    .toList();

            ctx.json(products);
        });

        // GET one product
        app.get("/products/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));

            Products products = productsDAO.getById(id);

            if (products == null) {
                ctx.status(404);
                return;
            }

            ctx.json(new ProductDTO(products));
        });

// CREATE product
        app.post("/products", ctx -> {
            Products product = ctx.bodyAsClass(Products.class);

            productsDAO.create(product);

            ctx.status(201);
            ctx.json(new ProductDTO(product));
        });

// UPDATE product
        app.put("/products/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));

            Products existingProduct = productsDAO.getById(id);

            if (existingProduct == null) {
                ctx.status(404);
                return;
            }

            Products updatedProduct = ctx.bodyAsClass(Products.class);

            existingProduct.setName(updatedProduct.getName());
            existingProduct.setRecipe(updatedProduct.getRecipe());
            existingProduct.setIngredients(updatedProduct.getIngredients());
            existingProduct.setPicture(updatedProduct.getPicture());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setAllergies(updatedProduct.getAllergies());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setStore_id(updatedProduct.getStore_id());
            existingProduct.setLastUse(updatedProduct.getLastUse());
            existingProduct.setPlu(updatedProduct.getPlu());

            Products savedProduct = productsDAO.update(existingProduct);

            ctx.json(new ProductDTO(savedProduct));
        });

        // DELETE product
        app.delete("/products/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));

            Products products = productsDAO.getById(id);

            if (products == null) {
                ctx.status(404);
                return;
            }

            productsDAO.delete(id);

            ctx.status(204);
        });
    }
}