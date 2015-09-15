package com.webonise.malladvertisementproject;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import utils.Constants;

/**
 * Created by webonise on 10/9/15.
 */
public class GeoFenceMonitoringService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {

    private String jsonResponse;
    private GsonBuilder builder;
    private Gson gson;
    private List<Geofence> geofenceList;
    private PendingIntent geofencePendingIntent;
    private GoogleApiClient googleApiClient;

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("GoogleApi", "onConnected");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("GoogleApi", "onConnected");
        int result = super.onStartCommand(intent, flags, startId);
        buildGoogleApiClient();
        googleApiClient.connect();
        jsonResponse = intent.getExtras().getString(Constants.JSON_RESPONSE);
        if(jsonResponse!=null)
        populateGeoFenceList(jsonResponse);

        return result;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("GoogleApi", "onConnected");
        if (googleApiClient.isConnected()) {
            Log.d("googleApiClient ", "connected");
            LocationServices.GeofencingApi.addGeofences(googleApiClient, getGeoFencingRequest(), getGeofencePendingIntent()).setResultCallback(this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("GoogleApi", "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("GoogleApi", "onConnectionFailed");
    }

    @Override
    public void onResult(Status status) {
        Log.d("GoogleApi", "onResult");
    }


    private PendingIntent getGeofencePendingIntent() {

        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        } else {
            Intent intent = new Intent(this, GeoFenceTransitionService.class);
            intent.putExtra(Constants.JSON_RESPONSE, jsonResponse);
            return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }

    private GeofencingRequest getGeoFencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofenceList);
        return builder.build();
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


    }

    public void populateGeoFenceList(String jsonResponse) {
        builder = new GsonBuilder();
        gson = builder.create();

        JsonDataParser jsonDataParser = new JsonDataParser();
        jsonDataParser = gson.fromJson(jsonResponse, JsonDataParser.class);


        geofenceList = new ArrayList<>();
        for (int i = 0; i < jsonDataParser.getOffers().size(); i++) {
            geofenceList.add(new Geofence.Builder()
                            .setRequestId(jsonDataParser.getOffers().get(i).getName())
                            .setCircularRegion(jsonDataParser.getOffers().get(i).getLatLng().latitude, jsonDataParser.getOffers().get(i).getLatLng().longitude, 1020)
                            .setExpirationDuration(Geofence.NEVER_EXPIRE)
                            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                            .build());

        }

    }
}
