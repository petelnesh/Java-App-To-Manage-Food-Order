package com.devmwatha.development.foodie.Models.Beans;

/**
 * Created by Mwatha on 01-Aug-16.
 */
public class HotelNames {
    private String image_Url;
    private String hotel_name,hotel_reviews;
    private String delivery_time,hotel_location;
    private String  hotel_id;

    public HotelNames() {
    }

    public HotelNames(String imageUrl,String delivery_time, String hotel_name,String hotel_location,String hotel_reviews,String hotel_id) {
        this.image_Url = imageUrl;
        this.hotel_name = hotel_name;
        this.hotel_location=hotel_location;
        this.delivery_time=delivery_time;
        this.hotel_reviews=hotel_reviews;
        this.hotel_id=hotel_id;
    }

    public String getImage_Url() {
        return image_Url;
    }

    public void setImage_Url(String image_Url) {
        this.image_Url = image_Url;
    }

    public String getHotel_name() {  return hotel_name;}

    public void setHotel_name(String hotel_name) {this.hotel_name = hotel_name; }

    public String getHotel_location() {return hotel_location;  }

    public void setHotel_location(String hotel_location) { this.hotel_location = hotel_location; }

    public String getHotel_reviews() { return hotel_reviews;}

    public void setHotel_reviews(String hotel_reviews) {   this.hotel_reviews = hotel_reviews;  }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }
}
