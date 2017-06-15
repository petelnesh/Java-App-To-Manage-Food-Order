package com.devmwatha.development.foodie.UI.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.devmwatha.development.foodie.Adapters.MyFoodAdapter;
import com.devmwatha.development.foodie.Configs.Configurations;
import com.devmwatha.development.foodie.Models.Beans.FoodItems;
import com.devmwatha.development.foodie.R;
import com.devmwatha.development.foodie.RecyclerViewItems.DividerItemDecoration;
import com.devmwatha.development.foodie.RecyclerViewItems.RecyclerItemClickListener;
import com.devmwatha.development.foodie.UI.Activities.DetailedFood;
import com.devmwatha.development.foodie.UI.Activities.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mwatha on 28-Jul-16.
 */
public class FragmentSoftDrinks extends Fragment {

    //Creating a List of foods
    private List<FoodItems> foodItemsList;
    public  String timer;
    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    Context context;
    FoodItems foodItems;

    JsonArrayRequest jsonArrayRequest;

    //Volley Request Queue
    private RequestQueue requestQueue;

    //The request counter to send initialization ?page=1, ?page=2  requests
    private int requestCount = 1;

    //Initialize view
    View view;

    public FragmentSoftDrinks() {
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_soft_drink,container,false);
 //RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_soft_drinks, container, false);
        //Initializing Views
        recyclerView = (RecyclerView)view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        //layoutManager= new GridLayoutManager(getActivity(),2);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //Calling method to get data to fetch data
        //getData();
        requestQueue = Volley.newRequestQueue(getActivity());
        getData();

        //adding on scroll listener to our recycler view
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
//Ifscrolled at last then
                    if (isLastItemDisplaying(recyclerView)) {
//Calling the method getdata again
                        getData();
                    }
                }
            });

        //Adding an scroll change listener to recyclerview
        // recyclerView.setOnScrollChangeListener(this);
        //Initializing our foodItems list
        foodItemsList = new ArrayList<>();

        //initializing our adapter
        adapter = new MyFoodAdapter(getActivity(), foodItemsList);

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        NetworkImageView imageView=((NetworkImageView)view.findViewById(R.id.food_image));
                        String imageurl= imageView.getImageURL();
                        String name=((TextView)view.findViewById(R.id.food_name)).getText().toString();
                        String desc=((TextView)view.findViewById(R.id.food_price)).getText().toString();
                        String food_desc=((TextView)view.findViewById(R.id.food_descriiption)).getText().toString();
                        timer=foodItems.getFood_delivery_time();
                        Intent intent=new Intent(getActivity(),DetailedFood.class);
                        intent.putExtra("name",name);
                        intent.putExtra("desc",desc);
                        intent.putExtra("image",imageurl);
                        intent.putExtra("food_desc",food_desc);
                        startActivity(intent);
                    }
                })
        );
        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    //Request to get json from server we are passing an integer here
    //This integer will used to specify the page number for the request ?page = requestcount
    //This method would return a JsonArrayRequest that will be added to the request queue
    private JsonArrayRequest getDataFromServer(int requestCount) {
        //Initializing ProgressBar
        final ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.progressBar1);

        //Displaying Progressbar
        progressBar.setVisibility(View.VISIBLE);
        // setProgressBarIndeterminateVisibility(true);

        String getArgument = getArguments().getString("id");

       String url="http://10.0.2.2/foodie/feed.php?page="+String.valueOf(requestCount)+"&hotelid="+getArgument;

        //String url="http://syncsoft.or.ke/foodie/foodie/feed.php?page="+String.valueOf(requestCount)+"&hotelid="+getArgument;


        //JsonArrayRequest of volley
        jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method parseData to parse the json response
                        parseData(response);
                        //Hiding the progressbar
                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        //If an error occurs that means end of the list has reached
                        Toast.makeText(getActivity(),"No more data here!",Toast.LENGTH_SHORT).show();
                           }
                });
        //Returning the request
        return jsonArrayRequest;
    }



    //This method will get data from the web api
    private void getData() {
        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromServer(requestCount));
        //Incrementing the request counter
        requestCount++;
    }

    //This method will parse json data
    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            //Creating the superhero object
           foodItems = new FoodItems();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the foodItems object
                foodItems.setImageUrl(json.getString(Configurations.SOFT_DRINK_IMAGE_URL));
                foodItems.setFood_description(json.getString(Configurations.SOFT_DRINK_DESCRIPTION));
                foodItems.setFood_name(json.getString(Configurations.SOFT_DRINK_NAME));
                foodItems.setFood_type(json.getString(Configurations.SOFT_DRINK_TYPE));
                foodItems.setFoood_price(json.getString(Configurations.SOFT_DRINK_PRICE));
                foodItems.setFood_delivery_time(json.getString(Configurations.SOFT_DRINK_TIMER));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the foodItems object to the list
            foodItemsList.add(foodItems);
        }

        //Notifying the adapter that data has been added or changed
        adapter.notifyDataSetChanged();
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


   }
