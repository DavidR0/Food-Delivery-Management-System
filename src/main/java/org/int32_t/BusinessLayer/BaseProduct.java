package org.int32_t.BusinessLayer;

public class BaseProduct extends MenuItem{
    /**
     * Constructor for the base product
     * @param rating
     * @param calories
     * @param protein
     * @param fat
     * @param sodium
     * @param price
     * @param title
     */
    public BaseProduct(float rating, int calories, int protein, int fat, int sodium, int price, String title) {
        this.rating = rating;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.sodium = sodium;
        this.price = price;
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
