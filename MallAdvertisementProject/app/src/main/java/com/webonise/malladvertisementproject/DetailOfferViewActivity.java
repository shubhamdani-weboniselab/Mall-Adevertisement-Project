package com.webonise.malladvertisementproject;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import utils.Constants;

/**
 * Created by webonise on 10/9/15.
 */
public class DetailOfferViewActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private TextView tvDescription;
    private TextView tvDiscount;
    private Button btnNavigation;
    private LocationManager manager;
    private String provider;
    private Location location;
    private boolean isGpsEnable;
    private boolean isNetworkEnable;
    private double userLongitude;
    private double userLatitude;
    private double destinationLongitude;
    private double destinationLatitude;
    private Bundle givenDataByPreviousActivity;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mBluetoothLeScanner;
    private BluetoothManager bluetoothManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_offer_view_activity);
        initializeView();
//        initializeBluetooth();
        givenDataByPreviousActivity = getIntent().getExtras();
        populateView(givenDataByPreviousActivity);
    }

    private boolean checkLocationEnable() {
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.d("provider", "enable");

            return true;
        } else {
//            enableLocation();
            return false;
        }
    }


    private void initializeView() {
        setToolBar();
        imageView = (ImageView) findViewById(R.id.detailImage);
        tvDescription = (TextView) findViewById(R.id.tvDetailText);
        tvDiscount = (TextView) findViewById(R.id.tvDiscount);
        btnNavigation = (Button) findViewById(R.id.btnNavigate);
        btnNavigation.setOnClickListener(this);

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle(getTitle());

    }

    private void populateView(Bundle givenDataByPreviousActivity) {
        tvDiscount.setText(givenDataByPreviousActivity.getString(Constants.DISCOUNT));
        tvDescription.setTextSize(20);
        tvDescription.setText(givenDataByPreviousActivity.getString(Constants.DESCRIPTION).toString());

        destinationLatitude = givenDataByPreviousActivity.getDouble(Constants.LATITUDE);
        destinationLongitude = givenDataByPreviousActivity.getDouble(Constants.LONGITUDE);
        Picasso.with(this).load(givenDataByPreviousActivity.getString(Constants.DETAIL_URL)).into(imageView);
    }

    public void openGoogleMapsForNavigation(Context context) {


        isGpsEnable = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isGpsEnable && !isNetworkEnable) {
//            enableLocation();
        } else {
            if (isNetworkEnable) {
                if (manager != null) {
                    location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        userLongitude = location.getLongitude();
                        userLatitude = location.getLatitude();
                    }
                }


            }
            if (isGpsEnable) {
                if (location == null) {

                    if (manager != null) {
                        location = manager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            userLatitude = location.getLatitude();
                            userLongitude = location.getLongitude();
                        }
                    }
                }
            }
        }

        String uri = String.format(Locale.ENGLISH, Constants.GOOGLE_URL
                , (float) userLatitude, (float) userLongitude, getString(R.string.your_location), destinationLatitude, destinationLongitude, getString(R.string.mall));

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void initializeBluetooth() {
        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

        mBluetoothAdapter = bluetoothManager.getAdapter();
//        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
    }


    @Override
    public void onClick(View view) {

        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, Constants.REQUEST_ENABLE_BT);
//            openGoogleMapsForNavigation(getApplicationContext());
        } else {
            openGoogleMapsForNavigation(getApplicationContext());
        }
    }

}



