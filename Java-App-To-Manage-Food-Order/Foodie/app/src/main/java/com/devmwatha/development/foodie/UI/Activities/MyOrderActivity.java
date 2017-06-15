package com.devmwatha.development.foodie.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.devmwatha.development.foodie.Adapters.MyOrderAdapter;
import com.devmwatha.development.foodie.Models.Beans.MyOrderData;
import com.devmwatha.development.foodie.Models.Databases.DatabaseHandler;
import com.devmwatha.development.foodie.R;
import com.devmwatha.development.foodie.RecyclerViewItems.DividerItemDecoration;
import com.devmwatha.development.foodie.RecyclerViewItems.RecyclerItemClickListener;

import java.util.List;
public class MyOrderActivity extends AppCompatActivity{
    RecyclerView selected_food_RecyclerView;
    List <MyOrderData> myOrderDataList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    TextView orderedItems,total_cost;

    private TextView items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        items= (TextView) findViewById(R.id.ordered_meals);
        selected_food_RecyclerView=(RecyclerView) findViewById(R.id.my_order_recycler_view) ;
        selected_food_RecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        selected_food_RecyclerView.setLayoutManager(layoutManager);
        selected_food_RecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new MyOrderAdapter(this, myOrderDataList);
        //Adding adapter to recyclerview
        selected_food_RecyclerView.setAdapter(adapter);
        TextView total,items;
        DatabaseHandler db= new DatabaseHandler(this);
        total=(TextView)findViewById(R.id.tv_total_amount);
        total.setText(String.valueOf(db.total()));

        items=(TextView) findViewById(R.id.ordered_meals);
        items.setText(String.valueOf(db.total_item()));

    }
public void editOrder(View view)
{
    Toast.makeText(this,"ready to edit the order",Toast.LENGTH_SHORT).show();
    selected_food_RecyclerView.addOnItemTouchListener(
            new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                @Override public void onItemClick(View view, int position) {
                    String name=((TextView)view.findViewById(R.id.foodname)).getText().toString();
                    String items=((TextView)view.findViewById(R.id.discount)).getText().toString();
                    String total=((TextView)view.findViewById(R.id.subtotal)).getText().toString();
                    String service_fee=((TextView)view.findViewById(R.id.service_fee)).getText().toString();
                    String delivery_fee=((TextView)view.findViewById(R.id.delivery_fee)).getText().toString();
                    Intent intent= new Intent(MyOrderActivity.this,DetailedFood.class);
                    int price=Integer.parseInt(total)/Integer.parseInt(items);
                    intent.putExtra("name",name);
                    intent.putExtra("items",items);
                    intent.putExtra("desc",String.valueOf(price));
                    intent.putExtra("service_fee",service_fee);
                    intent.putExtra("delivery_fee",delivery_fee);
                    intent.putExtra("food_desc","In edit mode you can not see the description");
                    startActivity(intent);
                }
            }));

}
    public void Placeorder(View view)
    {
        Toast.makeText(getApplicationContext(),"Yet to implement!",Toast.LENGTH_SHORT).show();
    }
}
