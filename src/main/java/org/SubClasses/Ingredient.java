package org.SubClasses;

public class Ingredient {

    private String mealId;
    private String productName;
    private int productWeight;
    private String mealCategory;

    public Ingredient(String mealId, String productName, int productWeight, String mealCategory) {
        this.mealId = mealId;
        this.productName = productName;
        this.productWeight = productWeight;
        this.mealCategory = mealCategory;

    }
    public String getMealId() {
        return mealId;
    }
    public String getProductName() {
        return productName;
    }
    public int getProductWeight() {
        return productWeight;
    }
    public String getMealCategory() {
        return mealCategory;
    }
}
