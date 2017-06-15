package com.devmwatha.development.foodie.UI.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.devmwatha.development.foodie.Adapters.ListHotelsAdapter;
import com.devmwatha.development.foodie.Configs.Configurations;
import com.devmwatha.development.foodie.Models.Beans.HotelNames;
import com.devmwatha.development.foodie.R;
import com.devmwatha.development.foodie.RecyclerViewItems.DividerItemDecoration;
import com.devmwatha.development.foodie.RecyclerViewItems.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListHotels extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    Context context;
    HotelNames hotelNames;
    private RequestQueue requestQueue;
    List<HotelNames> hotelNamesList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int requestCount = 1;

    public ListHotels() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.hotel_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView=(RecyclerView) findViewById(R.id.hotel_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        requestQueue = Volley.newRequestQueue(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
             @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                 if (isLastItemDisplaying(recyclerView)) {
                     //Calling the method getdata again
                     getData();
                 }
            }
        });
        //our Drawerlayout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        //navigationview responsible for showing the side drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        getData();
        hotelNamesList= new ArrayList<>();
        adapter = new ListHotelsAdapter(this, hotelNamesList);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        TextView hotel_view=(TextView) view.findViewById(R.id.hotel_name);
                        String name=hotel_view.getText().toString();
                        TextView hotel_id=(TextView) view.findViewById(R.id.hotelid);
                       String hotel_Id=hotel_id.getText().toString();
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtra("hotel_name",name);
                        intent.putExtra("id",hotel_Id);
                      startActivity(intent);

                                         }
                })
        );
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.american)
        {
        }
        if(id==R.id.asian)
        {
            startActivity(new Intent(this,CuisinesActivity.class));
        }
        if(id==R.id.myorder)
        {
           startActivity( new Intent(this, MyOrderActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void getData() {

        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromServer(requestCount));
        //Incrementing the request counter
        requestCount++;
    }
    private JsonArrayRequest getDataFromServer(int requestCount) {

                 JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Configurations.HOTEL_URL + String.valueOf(requestCount),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method parseData to parse the json response
                        parseData(response);
                        //Hiding the progressbar
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //If an error occurs that means end of the list has reached
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getApplicationContext(), "No more data available to load!", Toast.LENGTH_SHORT).show();


                    }
                });

        //Returning the request
        return jsonArrayRequest;
    }


    //This method will parse json data
    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            //Creating the superhero object
            hotelNames = new HotelNames();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the foodItems object
                hotelNames.setHotel_name(json.getString(Configurations.HOTEL_NAME));
                hotelNames.setHotel_location(json.getString(Configurations.HOTEL_LOCATION));
                hotelNames.setHotel_reviews(json.getString(Configurations.HOTEL_REVIEWS));
                hotelNames.setImage_Url(json.getString(Configurations.HOTEL_IMAGE_URL));
                hotelNames.setDelivery_time(json.getString(Configurations.DELIVERY_TIME));
                hotelNames.setHotel_id(json.getString(Configurations.HOTEL_ID));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the foodItems object to the list
            hotelNamesList.add(hotelNames);
        }

        //Notifying the adapter that data has been added or changed
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }


    //This method would check that the recyclerview scroll has reached the bottom or not
    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }

    @Override
    public void onRefresh() {
        getData();
    }
}
