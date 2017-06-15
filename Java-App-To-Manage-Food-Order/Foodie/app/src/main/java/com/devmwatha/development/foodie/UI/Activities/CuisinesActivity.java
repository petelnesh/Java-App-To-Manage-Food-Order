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
import com.devmwatha.development.foodie.Adapters.CuisinesAdapter;
import com.devmwatha.development.foodie.Adapters.ListHotelsAdapter;
import com.devmwatha.development.foodie.Configs.Configurations;
import com.devmwatha.development.foodie.Models.Beans.Cuisines;
import com.devmwatha.development.foodie.Models.Beans.HotelNames;
import com.devmwatha.development.foodie.R;
import com.devmwatha.development.foodie.RecyclerViewItems.DividerItemDecoration;
import com.devmwatha.development.foodie.RecyclerViewItems.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CuisinesActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    Context context;
    Cuisines cuisines;
    private RequestQueue requestQueue;
    List<Cuisines> cuisinesList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int requestCount = 1;

    public CuisinesActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_hotels);
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

        getData();
        cuisinesList= new ArrayList<>();
        adapter = new CuisinesAdapter(this, cuisinesList);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }

    public void moreCuisines(View view)
    {
        startActivity(new Intent(this,ListHotels.class));
    }


       private void getData() {

        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromServer(requestCount));
        //Incrementing the request counter
        requestCount++;
    }
    private JsonArrayRequest getDataFromServer(int requestCount) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Configurations.CUISINE_URL + String.valueOf(requestCount),
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
            cuisines = new Cuisines();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the foodItems object
                cuisines.setCuisine_name(json.getString(Configurations.CUISINE_NAME));
                cuisines.setDesc(json.getString(Configurations.CUISINE_DESC));
                cuisines.setCost(json.getString(Configurations.CUISINE_COST));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the foodItems object to the list
            cuisinesList.add(cuisines);
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
