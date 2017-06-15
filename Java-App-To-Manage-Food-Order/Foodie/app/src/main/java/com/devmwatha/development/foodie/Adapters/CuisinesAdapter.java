package com.devmwatha.development.foodie.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.devmwatha.development.foodie.Models.Beans.Cuisines;
import com.devmwatha.development.foodie.Models.Beans.HotelNames;
import com.devmwatha.development.foodie.R;
import com.devmwatha.development.foodie.UI.Activities.CustomVolleyRequest;

import java.util.List;

/**
 * Created by Mwatha on 01-Aug-16.
 */
public class CuisinesAdapter extends RecyclerView.Adapter<CuisinesAdapter.ViewHolder> {

    private Context context;
    List<Cuisines>  cuisinesList;

    public CuisinesAdapter(Context context, List<Cuisines> cuisinesList) {
        this.context = context;
        this.cuisinesList = cuisinesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main2, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CuisinesAdapter.ViewHolder holder, int position) {
        Cuisines cuisines=cuisinesList.get(position);
       holder.cuisine_name.setText(cuisines.getCuisine_name());
    }

    @Override
    public int getItemCount() {
        return cuisinesList.size() ;
    }

      class ViewHolder extends RecyclerView.ViewHolder {
          public TextView cuisine_name;
          public ViewHolder(View itemView) {
              super(itemView);

              cuisine_name=(TextView) itemView.findViewById(R.id.name_cuisine);
          }
      }
}