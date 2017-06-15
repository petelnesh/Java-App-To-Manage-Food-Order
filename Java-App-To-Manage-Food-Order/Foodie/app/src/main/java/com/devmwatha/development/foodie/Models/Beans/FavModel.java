package com.devmwatha.development.foodie.Models.Beans;

/**
 * Created by Mwatha on 17-Aug-16.
 */
public class FavModel {
    String fav_foodname, favhoelname;

    public FavModel(String fav_foodname, String favhoelname) {
        this.fav_foodname = fav_foodname;
        this.favhoelname = favhoelname;
    }

    public FavModel() {
    }

    public String getFav_foodname() {
        return fav_foodname;
    }

    public void setFav_foodname(String fav_foodname) {
        this.fav_foodname = fav_foodname;
    }
}
