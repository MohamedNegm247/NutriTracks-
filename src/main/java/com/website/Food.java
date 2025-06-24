package com.website;

public class Food {
    private int foodId;
    private String name;
    private String category;
    private float protein=0;
    private float carbs=0;
    private float fat=0;


    public Food() {}

    public Food(int foodId, String name, String category,
                       float protein, float carbs, float fat) {
        this.foodId = foodId;
        this.name = name;
        this.category = category;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getCarbs() {
        return carbs;
    }

    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }
}
