package com.webonise.malladvertisementproject;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import bluetooth.BeaconListenerService;
import utils.Constants;

/**
 * Created by webonise on 11/9/15.
 */
public class GeoFenceTransitionService extends IntentService {

    private NotificationManager notificationManager;

    public GeoFenceTransitionService() {
        super("GeoFenceTransitionService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        GsonBuilder builder;
        Gson gson;
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        String jsonResponse;
        builder = new GsonBuilder();
        gson = builder.create();
        JsonDataParser jsonDataParser = new JsonDataParser();
        List<Geofence> geofenceList;
        int listSize;
        List<JsonDataParser.OffersEntity> offersEntityList;
        String title = Constants.EMPTY_STRING;
        String description = Constants.EMPTY_STRING;
        String id = Constants.EMPTY_STRING;

        int geofenceTransition = geofencingEvent.getGeofenceTransition();


        if (geofencingEvent.hasError()) {
            Log.d("IntentService Error on GeoFencing ", "" + geofencingEvent.getErrorCode());
        }

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {

            Intent serviceIntent = new Intent(this, BeaconListenerService.class);
            startService(serviceIntent);

            jsonResponse = intent.getExtras().getString(Constants.JSON_RESPONSE);
            jsonDataParser = gson.fromJson(jsonResponse, JsonDataParser.class);
            offersEntityList = jsonDataParser.getOffers();
            geofenceList = geofencingEvent.getTriggeringGeofences();

            listSize = jsonDataParser.getOffers().size();
            for (int i = 0; i < listSize; i++) {

                if (TextUtils.equals(offersEntityList.get(i).getName(), geofenceList.get(i).getRequestId())) {
//                    title = offersEntityList.get(i).getName();
//                    description = offersEntityList.get(i).getDescription();
                    sendNotification(offersEntityList.get(i),offersEntityList.get(i).getId());
//                    id = offersEntityList.get(i).getId();
                    break;
                }
            }
            Log.d("IntentService Entering Occured", "");
            Toast.makeText(getBaseContext(), "" + geofenceTransition, Toast.LENGTH_LONG).show();


        } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {


            try{
                notificationManager.cancel(Integer.parseInt(id));
            }catch (Exception e) {
                e.printStackTrace();
            }

            Log.d("IntentService Entering Occured", "");
        }
    }

    private void sendNotification(JsonDataParser.OffersEntity entity,String id) {
        Log.d("IntentService inside sending Notification", "");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(entity.getName())
                .setContentText(entity.getDescription()).setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true);

        /*put the data in intent like did in adapter and then start the activity */
        Intent intent = new Intent(this, DetailOfferViewActivity.class);
        intent.putExtra(Constants.DETAIL_URL, entity.getUrl());
        intent.putExtra(Constants.DESCRIPTION, entity.getDescription());
        intent.putExtra(Constants.DISCOUNT, entity.getDiscountPercentage());
        intent.putExtra(Constants.LONGITUDE,Double.parseDouble(entity.getLongitude()));
        intent.putExtra(Constants.LATITUDE,Double.parseDouble(entity.getLatitude()));

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(DetailOfferViewActivity.class);
        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        notificationManager.notify(Integer.parseInt(id), builder.build());
    }
}
