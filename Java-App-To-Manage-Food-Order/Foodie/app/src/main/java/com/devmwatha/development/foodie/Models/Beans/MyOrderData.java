package com.devmwatha.development.foodie.Models.Beans;

import java.util.Date;

/**
 * Created by Mwatha on 07-Aug-16.
 */
public class MyOrderData {
    String meal_name;
    int transcation_id,total_cost,service_fee,delivery_fee,quantity;
    Date date_of_purchase;

    public MyOrderData() {
    }

    public MyOrderData(  int quantity,String meal_name, int cost,int delivery_fee,int service_fee) {
        this.meal_name = meal_name;
        this.total_cost = cost;
        this.service_fee=service_fee;
        this.delivery_fee=delivery_fee;
        this.quantity=quantity;
    }

    public String getMeal_name() {
        return meal_name;
    }

    public void setMeal_name(String meal_name) {
        this.meal_name = meal_name;
    }

    public Date getDate_of_purchase() {
        return date_of_purchase;
    }

    public void setDate_of_purchase(Date date_of_purchase) {
        this.date_of_purchase = date_of_purchase;
    }

    public int getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(int total_cost) {
        this.total_cost = total_cost;
    }

    public int getService_fee() {
        return service_fee;
    }

    public void setService_fee(int service_fee) {
        this.service_fee = service_fee;
    }

    public int getDelivery_fee() {
        return delivery_fee;
    }

    public void setDelivery_fee(int delivery_fee) {
        this.delivery_fee = delivery_fee;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTranscation_id() {
        return transcation_id;
    }

    public void setTranscation_id(int transcation_id) {
        this.transcation_id = transcation_id;
    }
}
