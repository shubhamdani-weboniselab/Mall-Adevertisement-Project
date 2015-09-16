package com.webonise.malladvertisementproject;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import adapters.RecycleViewAdapter;
import utils.Constants;


public class OffersListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private String jsonResponse;
    private GsonBuilder builder;
    private Gson gson;

    private TextView title;
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
        title = (TextView) findViewById(R.id.title);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        jsonResponse = getIntent().getExtras().getString(Constants.JSON_RESPONSE);
        builder = new GsonBuilder();
        gson = builder.create();
    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle(null);
        title.setText(getTitle());

    }

    private void setAdapter() {
       JsonDataParser jsonDataParser= new JsonDataParser();
        jsonDataParser = gson.fromJson(jsonResponse,JsonDataParser.class);
        final int arraySize = jsonDataParser.getOffers().size();
        String[] urlArray = new String[arraySize];
        String[] DescriptionArray = new String[arraySize];
        String[] DiscountArray = new String[arraySize];
        LatLng[] LatLngArray = new LatLng[arraySize];

        for (int i = 0; i < jsonDataParser.getOffers().size(); i++) {
            urlArray[i] = jsonDataParser.getOffers().get(i).getUrl().trim();
            DescriptionArray[i] = jsonDataParser.getOffers().get(i).getDescription();
            DiscountArray[i] = jsonDataParser.getOffers().get(i).getDiscountPercentage();
            LatLngArray[i] = jsonDataParser.getOffers().get(i).getLatLng();
        }
        adapter = new RecycleViewAdapter(DiscountArray, urlArray, DescriptionArray, LatLngArray, this);
        recyclerView.setAdapter(adapter);

    }

  }
