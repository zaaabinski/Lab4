package org.SubClasses;

public class Product {
    private String productName;
    private int carbs;
    private int protein;
    private int fats;
    private String category;

    public Product(String productName, int carbs, int protein, int fats, String category) {
        this.productName = productName;
        this.carbs = carbs;
        this.protein = protein;
        this.fats = fats;
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }
    public int getCarbs() {
        return carbs;
    }
    public int getProtein() {
        return protein;
    }
    public int getFats() {
        return fats;
    }
    public String getCategory() {
        return category;
    }

}
