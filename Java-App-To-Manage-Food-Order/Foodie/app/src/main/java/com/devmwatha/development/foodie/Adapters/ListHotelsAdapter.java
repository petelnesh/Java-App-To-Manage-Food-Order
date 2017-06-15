package com.devmwatha.development.foodie.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.devmwatha.development.foodie.Models.Beans.HotelNames;
import com.devmwatha.development.foodie.R;
import com.devmwatha.development.foodie.UI.Activities.CustomVolleyRequest;

import java.util.List;

/**
 * Created by Mwatha on 01-Aug-16.
 */
public class ListHotelsAdapter extends RecyclerView.Adapter<ListHotelsAdapter.ViewHolder> {

    private Context context;
    private ImageLoader imageloader;
    List<HotelNames> hotelNamesList;

    public ListHotelsAdapter(Context context, List<HotelNames> hotelNamesList) {
        this.context = context;
        this.hotelNamesList = hotelNamesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hotels_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListHotelsAdapter.ViewHolder holder, int position) {
        HotelNames hotelNames=hotelNamesList.get(position);
        imageloader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageloader.get(hotelNames.getImage_Url(), ImageLoader.getImageListener(holder.imageView, R.drawable.ic_holder, android.R.drawable.ic_dialog_alert));


        holder.imageView.setImageUrl(hotelNames.getImage_Url(), imageloader);
        holder.hotel_name.setText(hotelNames.getHotel_name());
        holder.hotel_location.setText(hotelNames.getHotel_location());
        holder.hotel_reviews.setText(hotelNames.getHotel_reviews()+" review(s)");
        holder.delivery_time.setText("Delivers in "+hotelNames.getDelivery_time()+ " minutes");
        holder.hotelid.setText(hotelNames.getHotel_id());
    }

    @Override
    public int getItemCount() {
        return hotelNamesList.size() ;
    }

      class ViewHolder extends RecyclerView.ViewHolder {
          public TextView hotel_name,
                  hotel_location,
                  hotelid,
                  hotel_reviews,
                  delivery_time;
          public NetworkImageView imageView;
          public ViewHolder(View itemView) {
              super(itemView);
              hotel_name=(TextView)itemView.findViewById(R.id.hotel_name);
              hotel_location=(TextView)itemView.findViewById(R.id.hotel_location);
              hotel_reviews=(TextView)itemView.findViewById(R.id.hotel_review);
              delivery_time=(TextView)itemView.findViewById(R.id.hotel_delivery_time);
              imageView=(NetworkImageView)itemView.findViewById(R.id.hotel_image);
              hotelid=(TextView)itemView.findViewById(R.id.hotelid);
          }
      }
}