package com.devmwatha.development.foodie.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.devmwatha.development.foodie.Adapters.FavAdapter;
import com.devmwatha.development.foodie.Models.Beans.FavModel;
import com.devmwatha.development.foodie.Models.Databases.DatabaseHandler;
import com.devmwatha.development.foodie.R;
import com.devmwatha.development.foodie.RecyclerViewItems.DividerItemDecoration;
import com.devmwatha.development.foodie.RecyclerViewItems.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class FavActivity extends AppCompatActivity {

    List<FavModel> favModelList;
    FavAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fav_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Favorite Foods");
        setUpRecycler();
    }
    private void setUpRecycler() {

        recyclerView =(RecyclerView) findViewById(R.id.fav_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        favModelList= new ArrayList<>();
        adapter = new FavAdapter(this, favModelList);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        //recyclerView.getChildAdapterPosition(view);
                        Toast.makeText(getApplicationContext(),"Item clicked "+recyclerView.getChildAdapterPosition(view),Toast.LENGTH_SHORT).show();
                    }
                    }));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fav_menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.clear)
        {
            Toast.makeText(this,"Clearing favorites ",Toast.LENGTH_SHORT).show();

            DatabaseHandler databaseHandler= new DatabaseHandler(this);
            databaseHandler.deleteFav();
            recyclerView.invalidate();
        }
        return super.onOptionsItemSelected(item);
    }
}
