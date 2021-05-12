package org.int32_t.BusinessLayer;

public class BaseProduct extends MenuItem{

    public BaseProduct(float rating, int calories, int protein, int fat, int sodium, int price, String title) {
        this.rating = rating;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
        this.price = price;
        this.title = title;
    }
}
