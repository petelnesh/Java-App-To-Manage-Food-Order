package com.devmwatha.development.foodie.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.devmwatha.development.foodie.Models.Beans.FavModel;
import com.devmwatha.development.foodie.Models.Beans.FoodItems;
import com.devmwatha.development.foodie.Models.Databases.DatabaseHandler;
import com.devmwatha.development.foodie.R;
import com.devmwatha.development.foodie.UI.Activities.CustomVolleyRequest;
import com.devmwatha.development.foodie.UI.Activities.FavActivity;

import java.util.List;



public class MyFoodAdapter extends RecyclerView.Adapter<MyFoodAdapter.ViewHolder> {
    //Imageloader to load image
    private ImageLoader imageLoader;
    private Context context;
    //List to store all superheroes
    RecyclerView recyclerView;
    List<FoodItems> foodItemsList;
    String name,cost,deliver,others,mm;

    //Constructor of this class

    public MyFoodAdapter(Context context, List<FoodItems> foodItemsList) {
        this.context = context;
        this.foodItemsList = foodItemsList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tile_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Getting the particular item from the list
        FoodItems foodItems =  foodItemsList.get(position);

        //Loading image from url
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(foodItems.getImageUrl(), ImageLoader.getImageListener(holder.food_image, R.drawable.thai_soup, android.R.drawable.ic_dialog_alert));

        //Loading images using picaso
        //Picasso.with(context).load(foodItemsList.get(position).getImageUrl()).resize(120, 60).rotate(0).into(holder.food_image);

        //Showing data on the views
        holder.food_image.setImageUrl(foodItems.getImageUrl(), imageLoader);
        holder.food_name.setText(foodItems.getFood_name());
        holder.food_price.setText(foodItems.getFoood_price());
        holder.food_price_label.setText("Ksh.");
        holder.desc_food.setText(foodItems.getFood_description());

        //Getting all the names
        name=foodItems.getFood_name();
        cost= foodItems.getFoood_price();

   }

    @Override
    public int getItemCount() {
        return foodItemsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //Views
       public NetworkImageView food_image;
        //public ImageView food_image;
        public TextView food_name;
        public TextView food_type;
        public  TextView food_price;
        public  TextView food_price_label,desc_food;
        public CheckBox isSelected,favorite;
        public ImageButton shareButton;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            food_image = (NetworkImageView) itemView.findViewById(R.id.food_image);
            food_name = (TextView) itemView.findViewById(R.id.food_name);
            food_price = (TextView)itemView.findViewById(R.id.food_price);
            food_price_label = (TextView)itemView.findViewById(R.id.food_price_label);
            desc_food=(TextView)itemView.findViewById(R.id.food_descriiption);

            isSelected=(CheckBox) itemView.findViewById(R.id.is_checked);
            favorite=(CheckBox) itemView.findViewById(R.id.favorite_button);
            shareButton=(ImageButton) itemView.findViewById(R.id.share_button);

            isSelected.setOnClickListener(new View.OnClickListener() {
                DatabaseHandler db=new DatabaseHandler(context);
                @Override
                public void onClick(View view) {
                    if(isSelected.isChecked()){
                        db.insertCheckOrder(name,Integer.parseInt(cost),1);
                        Toast.makeText(context,"Added to orders",Toast.LENGTH_SHORT).show();
                    }
                    if (!isSelected.isChecked())
                    {
                        db.delete(name);
                        Toast.makeText(context,"removed from orders",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DatabaseHandler databaseHandler= new DatabaseHandler(context);
                    if(favorite.isChecked()){
                        databaseHandler.insertFavorite(name);
                        Toast.makeText(context,"Adding to favorite",Toast.LENGTH_SHORT).show();

                    }
                    if (!favorite.isChecked())
                    {
                        Toast.makeText(context,"Removing from favorite",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

}