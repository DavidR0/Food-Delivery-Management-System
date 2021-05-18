package org.int32_t.BusinessLayer;

import java.util.List;

public class CompositeProduct extends MenuItem{
    private List<MenuItem> items;
    private String title;

    /**
     * Constructor for a composite product
     * @param items BaseProduct items list
     * @param title title of the product
     */
    public CompositeProduct(List<MenuItem> items,String title) {
        this.items = items;
        super.isBase = false;
        this.title = title;
        setComputedPrice();
    }

    /**
     * Updates the price of the product, taking into consideration the items list
     */
    private void setComputedPrice(){
        int price = 0;

        for(MenuItem prd : items){
            if(prd.isBase) {
                BaseProduct base = (BaseProduct) prd;
                price += base.computePrice();
            }
        }

        this.price = price;
    }

    /**
     *  @return Returns the items list of the composite product
     */
    public List<MenuItem> getItems() {
        return items;
    }

    /**
     * Sets the base products list
     * @param items list to be set
     */
    public void setItems(List<MenuItem> items) {
        this.items = items;
        setComputedPrice();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Converts a Composite product to a base product
     * @return BaseProduct
     */
    public BaseProduct convertToBase(){
        int calories = 0;
        float rating = 0;
        int protein = 0;
        int fat = 0;
        int sodium = 0;
        int price = 0;
        String title = "";

        for(MenuItem prd : items){
            if(prd.isBase) {
                BaseProduct base = (BaseProduct) prd;
                calories += base.getCalories();
                protein += base.getProtein();
                fat += base.getFat();
                sodium += base.getSodium();
                price += base.computePrice();
                title += base.getTitle() + " ";
                rating += base.getRating();
            }
        }
        rating = rating/items.size();

        return new BaseProduct(rating,calories,protein,fat,sodium,price,title);
    }
}
