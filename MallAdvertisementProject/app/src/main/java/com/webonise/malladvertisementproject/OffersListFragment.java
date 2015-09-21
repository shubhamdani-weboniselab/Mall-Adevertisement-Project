package com.webonise.malladvertisementproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import adapters.RecycleViewAdapter;
import adapters.ViewPagerAdapter;
import utils.Constants;

/**
 * Created by webonise on 18/9/15.
 */
public class OffersListFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private String jsonResponse;
    private GsonBuilder builder;
    private Gson gson;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;


    public OffersListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void initialiseViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        jsonResponse = OffersListActivity.jsonResponse;
        builder = new GsonBuilder();
        gson = builder.create();
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.offer_list_fragment, container, false);
        initialiseViews(view);
        setAdapter();
        return view;
    }

    //    private void setAdapter() {
//        JsonDataParser jsonData = new JsonDataParser();
//        jsonData = gson.fromJson(jsonResponse, JsonDataParser.class);
//        final int arraySize = jsonData.getOffers().size();
//        String[] urlArray = new String[arraySize];
//        String[] DescriptionArray = new String[arraySize];
//        String[] DiscountArray = new String[arraySize];
//        LatLng[] LatLngArray = new LatLng[arraySize];
//        for (int i = 0; i < jsonData.getOffers().size(); i++) {
//            urlArray[i] = jsonData.getOffers().get(i).getUrl().trim();
//            DescriptionArray[i] = jsonData.getOffers().get(i).getDescription();
//            DiscountArray[i] = "10% discount";
//            LatLngArray[i] = jsonData.getOffers().get(i).getLatLng();
//        }
//        adapter = new RecycleViewAdapter(DiscountArray, urlArray, DescriptionArray, LatLngArray, getActivity());
//        recyclerView.setAdapter(adapter);
//        viewPagerAdapter = new ViewPagerAdapter( jsonData.getUrls(),getActivity());
//        viewPager.setAdapter(viewPagerAdapter);
//    }
    private void setAdapter() {
        JsonData jsonData = new JsonData();
        jsonData = gson.fromJson(jsonResponse, JsonData.class);
        final int arraySize = jsonData.getData().size();
        String[] urlArray = new String[arraySize];
        String[] DescriptionArray = new String[arraySize];
        String[] DiscountArray = new String[arraySize];
        LatLng[] LatLngArray = new LatLng[arraySize];

        for (int i = 0; i < jsonData.getData().size(); i++) {
            urlArray[i] = Constants.BASE_URL + jsonData.getData().get(i).getImageUrl().trim();
            DescriptionArray[i] = jsonData.getData().get(i).getEventDescription();
            DiscountArray[i] = "10% discount";
            LatLngArray[i] = new LatLng(jsonData.getData().get(i).getGeofenceData().getLat(), jsonData.getData().get(i).getGeofenceData().getLng());
        }
        viewPagerAdapter = new ViewPagerAdapter(jsonData.getUrls(), getActivity());
        viewPager.setAdapter(viewPagerAdapter);
        adapter = new RecycleViewAdapter(DiscountArray, urlArray, DescriptionArray, LatLngArray, getActivity());
        recyclerView.setAdapter(adapter);


    }
}
