package com.example.magicrecipe.model;

public class Recipe {

    public String title;
    public String ingredients;
    public String image;
    public String web;

    public Recipe(String title, String ingredients, String image, String web) {
        this.title = title;
        this.ingredients = ingredients;
        this.image = image;
        this.web = web;
    }
}
