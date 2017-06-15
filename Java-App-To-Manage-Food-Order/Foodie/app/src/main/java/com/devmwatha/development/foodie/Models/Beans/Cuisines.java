package com.devmwatha.development.foodie.Models.Beans;

/**
 * Created by Mwatha on 16-Aug-16.
 */
public class Cuisines {
    String cuisine_name;
    String desc,cost;


    public Cuisines(String name,String desc, String cost ) {
        this.cost=cost;
        this.desc=desc;
        this.cuisine_name = name;
    }

    public Cuisines() {
    }

    public String getCuisine_name() {
        return cuisine_name;
    }

    public void setCuisine_name(String cuisine_name) {
        this.cuisine_name = cuisine_name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
