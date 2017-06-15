package com.devmwatha.development.foodie.Models.Beans;

public class FoodItems {
    //Data Variables
    private String imageUrl;
    private String food_name;
    private String food_type;
    private String food_delivery_time;
    private String foood_price;
    private  String food_description;

    public FoodItems() {
    }

    public FoodItems(String imageUrl, String food_name, String food_type, String foood_price,String food_description,String food_delivery_time) {
        this.imageUrl = imageUrl;
        this.food_name = food_name;
        this.food_type = food_type;
        this.foood_price = foood_price;
        this.food_description=food_description;
        this.food_delivery_time= food_delivery_time;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_type() {
        return food_type;
    }

    public void setFood_type(String food_type) {
        this.food_type = food_type;
    }

    public String getFoood_price() {
        return foood_price;
    }

    public void setFoood_price(String foood_price) {
        this.foood_price = foood_price;
    }

    public String getFood_description() {return food_description;}

    public void setFood_description(String food_description) { this.food_description = food_description;}

    public String getFood_delivery_time() {return food_delivery_time;}

    public void setFood_delivery_time(String food_delivery_time) {this.food_delivery_time = food_delivery_time;}
}
