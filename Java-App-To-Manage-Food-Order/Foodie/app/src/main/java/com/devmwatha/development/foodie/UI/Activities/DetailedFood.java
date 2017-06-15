package com.devmwatha.development.foodie.UI.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.devmwatha.development.foodie.Models.Databases.DatabaseHandler;
import com.devmwatha.development.foodie.R;

public class DetailedFood extends AppCompatActivity {
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;
    TextView textView;
    String food_name;
    TextView price_food;
    TextView total_price;
    int total_sum;
    int my_amount;
Activity activity;
    int limit=10;
    int currentitems;
    DatabaseHandler databaseHandler;
    Button cartButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_food);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRequestQueue = Volley.newRequestQueue(this);
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });

        //initializing this activity here
        activity=this;

        //passing data from one activity to the next
        Intent intent=getIntent();
        food_name= intent.getExtras().getString("name");
        String price=intent.getExtras().getString("desc");
        String imageurl=intent.getExtras().getString("image");
        String food_description=intent.getExtras().getString("food_desc");
        String items=intent.getExtras().getString("items");
        TextView detailedfood_name=(TextView) findViewById(R.id.name_of_food);
        TextView detailedfood_desc=(TextView) findViewById(R.id.describe_food);
        total_price=(TextView) findViewById(R.id.total_cost);
        price_food=(TextView) findViewById(R.id.price);
        NetworkImageView imageView=(NetworkImageView) findViewById(R.id.foodieimage);
        detailedfood_desc.setText(food_description);
        detailedfood_name.setText(food_name);
        price_food.setText(price);
        total_price.setText(price);
        imageView.setImageUrl(imageurl,mImageLoader);
        textView=(TextView) findViewById(R.id.tv_no_of_meal);
        textView.setText("1");
        cartButton=(Button) findViewById(R.id.addtocart);
        //toolbar.setTitle(food_name);
    }

    public void onClickAdd(View view) {
             currentitems=Integer.parseInt(textView.getText().toString());
            if (currentitems<limit){
                currentitems++;
                textView.setText(String.valueOf(currentitems));
                total_sum=Integer.parseInt(price_food.getText().toString());
                my_amount=total_sum*currentitems;
printTotal();
            }
        else
                if(currentitems==limit)
                {

                    Snackbar.make(view, "You are not allowed to order more than 10 items for each", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
    }


    public void onClickDeduct(View view)
    {
        currentitems=Integer.parseInt(textView.getText().toString());
        if(currentitems==1){
            textView.setText(String.valueOf(currentitems));
            Snackbar.make(view, "1 is the minimum number you can order", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            total_sum=Integer.parseInt(price_food.getText().toString());
            my_amount=total_sum*currentitems;
            printTotal();
        }
        else
        currentitems--;
        textView.setText(String.valueOf(currentitems));
        total_sum=Integer.parseInt(price_food.getText().toString());
        my_amount=total_sum*currentitems;
        printTotal();
    }

    private void printTotal() {
        total_price.setText(String.valueOf(my_amount));
            }

    public void addToCart(View v)
    {
        DatabaseHandler dbh= new DatabaseHandler(getApplicationContext());
        dbh.insertOrder(food_name,my_amount,currentitems,0,0);
        finish();
    }
}
