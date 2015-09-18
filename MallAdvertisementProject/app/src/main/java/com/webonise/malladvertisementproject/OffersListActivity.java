package com.webonise.malladvertisementproject;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import adapters.RecycleViewAdapter;
import adapters.ViewPagerAdapter;
import utils.Constants;


public class OffersListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private String jsonResponse;
    private GsonBuilder builder;
    private Gson gson;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer_list_activity);
        setToolBar();
        initialiseViews();
        setAdapter();

    }

    private void initialiseViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        jsonResponse = getIntent().getExtras().getString(Constants.JSON_RESPONSE);
        builder = new GsonBuilder();
        gson = builder.create();

        viewPager = (ViewPager) findViewById(R.id.viewPager);

    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle(null);


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
private void setAdapter() {
    JsonDataParser jsonData = new JsonDataParser();
    jsonData = gson.fromJson(jsonResponse, JsonDataParser.class);
    final int arraySize = jsonData.getOffers().size();
    String[] urlArray = new String[arraySize];
    String[] DescriptionArray = new String[arraySize];
    String[] DiscountArray = new String[arraySize];
    LatLng[] LatLngArray = new LatLng[arraySize];

    for (int i = 0; i < jsonData.getOffers().size(); i++) {
        urlArray[i] = jsonData.getOffers().get(i).getUrl().trim();
        DescriptionArray[i] = jsonData.getOffers().get(i).getDescription();
        DiscountArray[i] = "10% discount";
        LatLngArray[i] = jsonData.getOffers().get(i).getLatLng();
    }
    adapter = new RecycleViewAdapter(DiscountArray, urlArray, DescriptionArray, LatLngArray, this);
    recyclerView.setAdapter(adapter);

    viewPagerAdapter = new ViewPagerAdapter( jsonData.getUrls(),this);
    viewPager.setAdapter(viewPagerAdapter);
}

}
