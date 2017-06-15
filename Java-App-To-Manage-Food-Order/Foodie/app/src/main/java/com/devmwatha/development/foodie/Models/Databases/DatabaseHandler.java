package com.devmwatha.development.foodie.Models.Databases;

/**
 * Created by Mwatha on 08-Aug-16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.devmwatha.development.foodie.Models.Beans.FavModel;
import com.devmwatha.development.foodie.Models.Beans.MyOrderData;

import java.util.ArrayList;
import java.util.List;

public  class DatabaseHandler extends SQLiteOpenHelper {


    //database creation
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "my_orderDB.db";

    //orders  table
    public static final String TABLE_ORDERS = "my_orders";
    public static final String COLUMN_FOOD_NAME = "KEY_NAME";
    public static final String COLUMN_COST = "KEY_COST";
    public static final String COLUMN_SERVICE_FEE = "KEY_SERVICE_FEE";
    public static final String COLUMN_DELIVERY_FEE = "KEY_DELIVERY_FEE";
    public static final String COLUMN_QUANTITY = "KEY_QUANTITY";



    public static final String TABLE_FAVORITE = "favorites";
    public static final String COLUMN_FAV_NAME = "FAV_NAME";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ACCOUNTS_TABLE = "CREATE TABLE "+TABLE_ORDERS+"(KEY_ID INTEGER PRIMARY KEY autoincrement,KEY_NAME TEXT,KEY_QUANTITY INTEGER,KEY_COST INTEGER,KEY_SERVICE_FEE INTEGER,KEY_DELIVERY_FEE INTEGER);";
        String CREATE_FAVS_TABLE = "CREATE TABLE "+TABLE_FAVORITE+"(_id INTEGER PRIMARY KEY autoincrement,FAV_NAME TEXT);";
        db.execSQL(CREATE_ACCOUNTS_TABLE);
        db.execSQL(CREATE_FAVS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        onCreate(db);
    }


    public void insertOrder(String food_name, int cost, int quantity, int service_fee,int delivery_fee)
    {
        SQLiteDatabase db = this.getWritableDatabase();
       ContentValues values = new ContentValues();
        values.put(COLUMN_FOOD_NAME,food_name);
        values.put(COLUMN_COST,cost);
        values.put(COLUMN_QUANTITY,quantity);
        values.put(COLUMN_SERVICE_FEE,service_fee);
        values.put(COLUMN_DELIVERY_FEE,delivery_fee);
        // Inserting Row
        db.insert(TABLE_ORDERS, null, values);
        db.close(); // Closing database connection
    }

    public void insertFavorite(String fav_name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FAV_NAME,fav_name);
        db.insert(TABLE_FAVORITE, null, values);
        db.close(); // Closing database connection
    }

    public void insertCheckOrder(String food_name, int cost,int items)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FOOD_NAME,food_name);
        values.put(COLUMN_COST,cost);
        values.put(COLUMN_QUANTITY,items);
        // Inserting Row
        db.insert(TABLE_ORDERS, null, values);
        db.close(); // Closing database connection
    }

    public Integer delete(String id)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        return  db.delete(TABLE_ORDERS,"KEY_NAME = ?",new String[] {id});
    }

    public Integer deleteFav()
    {
        SQLiteDatabase db= this.getWritableDatabase();
        return  db.delete(TABLE_FAVORITE,null,null);
    }

    /**
     * Getting all labels
     * returns list of labels
     * */
    public List<MyOrderData> getAllLabels(){
        List<MyOrderData> labels = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ORDERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext())
             {
                 MyOrderData myOrderData= new MyOrderData();
                 myOrderData.setMeal_name(cursor.getString(1));
                 myOrderData.setQuantity(cursor.getInt(2));
                 myOrderData.setTotal_cost(cursor.getInt(3));
                 myOrderData.setService_fee(cursor.getInt(4));
                 myOrderData.setDelivery_fee(cursor.getInt(5));
                 labels.add(myOrderData);
            }
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return labels;
    }

    public int total(){
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ORDERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int total=0;
        int all=0;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext())
            {
                total=cursor.getInt(3);
                all=total+all;
            }
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return all ;
    }

    public int total_item(){
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ORDERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int total=0;
        int all=0;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext())
            {
                total=cursor.getInt(2);
                all=total+all;
            }
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return all ;
    }



    public List<FavModel> getFav(){
        List<FavModel> favModelList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FAVORITE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext())
            {
                FavModel favModel= new FavModel();
                favModel.setFav_foodname(cursor.getString(1));
                favModelList.add(favModel);
            }
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return favModelList;
    }

}