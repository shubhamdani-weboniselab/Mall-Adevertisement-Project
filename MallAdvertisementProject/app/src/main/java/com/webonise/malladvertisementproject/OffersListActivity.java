package com.webonise.malladvertisementproject;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import utils.Constants;


public class OffersListActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    static public String jsonResponse;
    private GsonBuilder builder;
    private Gson gson;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private Toolbar toolbar;
    private TabLayout tabLayout;

    private ImageView imgToolbarRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer_list_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setToolBar();
        initialiseViews();
//        setAdapter();
    }

    private void initialiseViews() {
        jsonResponse = getIntent().getExtras().getString(Constants.JSON_RESPONSE);
        viewPager = (ViewPager) findViewById(R.id.OfferViewPager);
        setupViewPager(viewPager);
        imgToolbarRefresh = (ImageView) findViewById(R.id.imgToolbarRefresh);
        imgToolbarRefresh.setOnClickListener(this);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle(null);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OffersListFragment(), "ONE");
        adapter.addFragment(new OffersListFragment(), "TWO");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void onRefresh() {

    }


    //    private void setAdapter() {
//        JsonData jsonData = new JsonData();
//        jsonData = gson.fromJson(jsonResponse, JsonData.class);
//        final int arraySize = jsonData.getData().size();
//        String[] urlArray = new String[arraySize];
//        String[] DescriptionArray = new String[arraySize];
//        String[] DiscountArray = new String[arraySize];
//        LatLng[] LatLngArray = new LatLng[arraySize];
//
//        for (int i = 0; i < jsonData.getData().size(); i++) {
//            urlArray[i] = Constants.BASE_URL + jsonData.getData().get(i).getImageUrl().trim();
//            DescriptionArray[i] = jsonData.getData().get(i).getEventDescription();
//            DiscountArray[i] = "10% discount";
//            LatLngArray[i] = new LatLng(jsonData.getData().get(i).getGeofenceData().getLat(), jsonData.getData().get(i).getGeofenceData().getLng());
//        }
//        adapter = new RecycleViewAdapter(DiscountArray, urlArray, DescriptionArray, LatLngArray, this);
//        recyclerView.setAdapter(adapter);
//
//    }
//    private void setAdapter() {
//        JsonDataParser jsonData = new JsonDataParser();
//        jsonData = gson.fromJson(jsonResponse, JsonDataParser.class);
//        final int arraySize = jsonData.getOffers().size();
//        String[] urlArray = new String[arraySize];
//        String[] DescriptionArray = new String[arraySize];
//        String[] DiscountArray = new String[arraySize];
//        LatLng[] LatLngArray = new LatLng[arraySize];
//
//        for (int i = 0; i < jsonData.getOffers().size(); i++) {
//            urlArray[i] = jsonData.getOffers().get(i).getUrl().trim();
//            DescriptionArray[i] = jsonData.getOffers().get(i).getDescription();
//            DiscountArray[i] = "10% discount";
//            LatLngArray[i] = jsonData.getOffers().get(i).getLatLng();
//        }
//        adapter = new RecycleViewAdapter(DiscountArray, urlArray, DescriptionArray, LatLngArray, this);
//        recyclerView.setAdapter(adapter);
//
//        viewPagerAdapter = new ViewPagerAdapter(jsonData.getUrls(), this);
//        viewPager.setAdapter(viewPagerAdapter);
//    }

    @Override
    public void onClick(View view) {

        Toast.makeText(this, "Reloading", Toast.LENGTH_LONG).show();
    }
}
