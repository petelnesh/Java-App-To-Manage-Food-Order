package com.devmwatha.development.foodie.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.devmwatha.development.foodie.R;
import com.devmwatha.development.foodie.UI.Fragments.FragmentDessert;
import com.devmwatha.development.foodie.UI.Fragments.FragmentHotDrink;
import com.devmwatha.development.foodie.UI.Fragments.FragmentMainDish;
import com.devmwatha.development.foodie.UI.Fragments.FragmentSalad;
import com.devmwatha.development.foodie.UI.Fragments.FragmentSoftDrinks;
import com.devmwatha.development.foodie.UI.Fragments.FragmentSoup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    public String name;
    public String id;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent= getIntent();

        name=intent.getExtras().getString("hotel_name");
        id=intent.getExtras().getString("id");

        StoreHotelID storeHotelID=new StoreHotelID(id);
        storeHotelID.setId(id);


        setTitle(name);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //setting up the tab layout and view pager responsible for populating data in the main page
        viewPager=(ViewPager) findViewById(R.id.viewpager);
        setUpViewPager(viewPager);

        //tab layout
        tabLayout=(TabLayout) findViewById(R.id.tab_layout);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                tabLayout.animate();
            }
        });
    }

    public MainActivity() {
    }

    private void setUpViewPager(ViewPager viewPager) {

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        //passing the  value of the hotel to the fragment to fetch data using it
        // create an instance of the fragment then pass the hotel id in the Bundle
        FragmentSoftDrinks fragmentSoftDrinks = new FragmentSoftDrinks();
        Bundle args = new Bundle();
        args.putString("id",id);
        fragmentSoftDrinks.setArguments(args);

        FragmentHotDrink fragmentHotDrink = new FragmentHotDrink();
        Bundle hotargs = new Bundle();
        hotargs.putString("id",id);
        fragmentHotDrink.setArguments(hotargs);

        FragmentDessert fragmentDessert= new FragmentDessert();
        Bundle desertArgs= new Bundle();
        desertArgs.putString("id",id);
        fragmentDessert.setArguments(desertArgs);


        FragmentMainDish fragmentMainDish= new FragmentMainDish();
        Bundle mainArgs=new Bundle();
        mainArgs.putString("id",id);
        fragmentMainDish.setArguments(mainArgs);


        FragmentSalad fragmentSalad= new FragmentSalad();
        Bundle saladArgs= new Bundle();
        saladArgs.putString("id",id);
        fragmentSalad.setArguments(saladArgs);

        FragmentSoup fragmentSoup= new FragmentSoup();
        Bundle soupArgs= new Bundle();
        soupArgs.putString("id", id);
        fragmentSoup.setArguments(soupArgs);

        viewPagerAdapter.addFragment(fragmentSoftDrinks ,"Soft  Drinks");
        viewPagerAdapter.addFragment(fragmentHotDrink, "Hot  Drinks");
        viewPagerAdapter.addFragment(fragmentMainDish, "Main  Course");
        viewPagerAdapter.addFragment(fragmentSoup, "\tSoups\t\t");
        viewPagerAdapter.addFragment(fragmentSalad, "\tSalads\t\t");
        viewPagerAdapter.addFragment(fragmentDessert, "\tDesserts\t");

        //set an adapter for the view pager to display the various fragment as indicated here above
        viewPager.setAdapter(viewPagerAdapter);

    }
    public void shareFoodie(View view) {
        Intent shareFoodie=new Intent();
        shareFoodie.setAction(Intent.ACTION_SEND);
        shareFoodie.putExtra(Intent.EXTRA_TEXT, "Sharing to all of my friends!");
        shareFoodie.setType("text/plain");
        startActivity(Intent.createChooser(shareFoodie, getResources().getText(R.string.send_to)));

    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragmentList = new ArrayList<>();
        private List<String> mTabTitle = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitle.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mTabTitle.add(title);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.nav_order)
        {
            startActivity(new Intent(this,MyOrderActivity.class));
        }

        if(id==R.id.nav_track)
        {
            startActivity(new Intent (this,TrackOrder.class));
        }
        if(id==R.id.nav_fav)
        {
            startActivity(new Intent (this,FavActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
