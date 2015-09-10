package com.webonise.malladvertisementproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_offer_view_activity);
        initializeView();
        Bundle givenDataByPreviousActivity = getIntent().getExtras();
        populateView(givenDataByPreviousActivity);

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

    private void initializeView() {
        setToolBar();
        imageView = (ImageView) findViewById(R.id.detailImage);
        tvDescription = (TextView) findViewById(R.id.tvDetailText);
        tvDiscount = (TextView) findViewById(R.id.tvDiscount);
        btnNavigation = (Button) findViewById(R.id.btnNavigate);
        btnNavigation.setOnClickListener(this);
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
        Picasso.with(this).load(givenDataByPreviousActivity.getString("url")).into(imageView);
    }

    public void openGoogleMapsForNavigation(Context context) {

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        isGpsEnable = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isGpsEnable && !isNetworkEnable) {
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

    @Override
    public void onClick(View view) {
        openGoogleMapsForNavigation(getApplicationContext());
    }
}



