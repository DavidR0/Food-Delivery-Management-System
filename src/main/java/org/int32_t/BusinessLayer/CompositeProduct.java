package org.int32_t.BusinessLayer;

import java.text.DecimalFormat;
import java.util.List;

public class CompositeProduct extends MenuItem{
    private List<MenuItem> items;
    private String title;

    public CompositeProduct(List<MenuItem> items,String title) {
        this.items = items;
        super.isBase = false;
        this.title = title;
        setComputedPrice();
    }

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

    public List<MenuItem> getItems() {
        return items;
    }

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
