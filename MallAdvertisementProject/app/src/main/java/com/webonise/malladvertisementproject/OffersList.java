package com.webonise.malladvertisementproject;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import adapters.RecycleViewAdapter;
import utils.CoOdrinates;
import utils.Constants;


public class OffersList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private String jsonResponse;
    private GsonBuilder builder;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolBar();
        initialiseViews();
        setAdapter();
        if(!isMyServiceRunning(GeoFenceMoniteringService.class)){
            Intent intent = new Intent(this,GeoFenceMoniteringService.class);
            intent.putExtra("jsonResponse",jsonResponse);
            startService(intent);
        }
    }

    private void initialiseViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        jsonResponse = getIntent().getExtras().getString(Constants.SERVER_DATA);
        builder = new GsonBuilder();
        gson = builder.create();
    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle(getTitle());

    }

    private void setAdapter() {
        JsonDataParser jsonDataParser = new JsonDataParser();
        jsonDataParser = gson.fromJson(jsonResponse, JsonDataParser.class);
        final int arraySize = jsonDataParser.getOffers().size();
        String[] urlArray = new String[arraySize];
        String[] DescriptionArray = new String[arraySize];
        String[] DiscountArray = new String[arraySize];
        CoOdrinates[] coOrdinateArray = new CoOdrinates[arraySize];

//
        for (int i = 0; i < jsonDataParser.getOffers().size(); i++) {
            urlArray[i] = jsonDataParser.getOffers().get(i).getUrl().trim();
            DescriptionArray[i] = jsonDataParser.getOffers().get(i).getDescription();
            DiscountArray[i] = jsonDataParser.getOffers().get(i).getDiscountPercentage();
            coOrdinateArray[i] = jsonDataParser.getOffers().get(i).getCoOrdinates();
        }
        adapter = new RecycleViewAdapter(DiscountArray, urlArray, DescriptionArray, coOrdinateArray, this);
        recyclerView.setAdapter(adapter);

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
