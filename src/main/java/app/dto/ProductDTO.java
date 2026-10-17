package app.dto;

import app.entities.Products;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ProductDTO {

    private long id;

    private String name;

    private String picture;

    private String price;

    private String recipe;

    private String allergies;

    private String ingredients;

    private int store_id;

    private String category;

    private String lastUse;

    private int plu;

    public ProductDTO(Products product) {
        this.id = product.getId();
        this.name = product.getName();
        this.picture = product.getPicture();
        this.price = product.getPrice();
        this.recipe = product.getRecipe();
        this.allergies = product.getAllergies();
        this.ingredients = product.getIngredients();
        this.store_id = product.getStore_id();
        this.category = product.getCategory();
        this.lastUse = product.getLastUse();
        this.plu = product.getPlu();
    }

}

