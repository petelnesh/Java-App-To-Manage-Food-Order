package com.devmwatha.development.foodie.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.devmwatha.development.foodie.Models.Beans.MyOrderData;
import com.devmwatha.development.foodie.Models.Databases.DatabaseHandler;
import com.devmwatha.development.foodie.R;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    private Context context;
    //List to store all superheroes
    List <MyOrderData> arrayList;
    int total=0,total_item=0;
    DatabaseHandler databaseHandler;
    //Constructor of this class
    public MyOrderAdapter(Context context, List <MyOrderData> myOrderDataList) {
        this.context = context;
        this.arrayList = myOrderDataList;
        databaseHandler= new DatabaseHandler(context);
        arrayList=databaseHandler.getAllLabels();
    }

    public MyOrderAdapter() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orderlayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyOrderData myOrderData=arrayList.get(position);
        holder.food_name.setText(myOrderData.getMeal_name());
        holder.subtotal.setText(String.valueOf(myOrderData.getTotal_cost()));
        holder.servicefee.setText(String.valueOf(myOrderData.getService_fee()));
        holder.delivery_fee.setText(String.valueOf(myOrderData.getDelivery_fee()));
        holder.items.setText(String.valueOf(myOrderData.getQuantity()));
//calculating the cost of the food ordered
        int cost=myOrderData.getTotal_cost();
        int item=myOrderData.getQuantity();

        }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //Views
        public TextView food_name;
        public TextView items;
        public  TextView subtotal;
        public  TextView servicefee,delivery_fee;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            items = (TextView) itemView.findViewById(R.id.discount);
            food_name = (TextView) itemView.findViewById(R.id.foodname);
            subtotal = (TextView) itemView.findViewById(R.id.subtotal);
            servicefee = (TextView)itemView.findViewById(R.id.service_fee);
            delivery_fee = (TextView)itemView.findViewById(R.id.delivery_fee);

        }
    }
}