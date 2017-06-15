package com.devmwatha.development.foodie.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devmwatha.development.foodie.Models.Beans.FavModel;
import com.devmwatha.development.foodie.Models.Beans.MyOrderData;
import com.devmwatha.development.foodie.Models.Databases.DatabaseHandler;
import com.devmwatha.development.foodie.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {
    private Context context;
    //List to store all superheroes
    List <FavModel> arrayList;
    DatabaseHandler databaseHandler;
    //Constructor of this class
    public FavAdapter(Context context, List <FavModel> myOrderDataList) {
        this.context = context;
        this.arrayList = myOrderDataList;
        databaseHandler= new DatabaseHandler(context);
        arrayList=databaseHandler.getFav();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FavModel favModel=arrayList.get(position);
        holder.food_name.setText(favModel.getFav_foodname());
        Date date= new Date();
        //SimpleDateFormat simpleDateFormat= new SimpleDateFormat(date);
        holder.date.setText(String.valueOf(date));
        }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //Views
        public TextView food_name,date;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            food_name = (TextView) itemView.findViewById(R.id.fav_food_name);
            date=(TextView) itemView.findViewById(R.id.date);

        }
    }
}