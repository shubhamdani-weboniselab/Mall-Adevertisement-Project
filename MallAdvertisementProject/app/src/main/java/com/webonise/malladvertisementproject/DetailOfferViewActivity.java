package com.webonise.malladvertisementproject;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
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
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
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

    private ImageView expandedImageView;
    private Animator currentAnimator;
    private int mShortAnimationDuration;


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

        expandedImageView = (ImageView)findViewById(R.id.expandedImage);
        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        imageView.setOnClickListener(this);
        expandedImageView.setOnClickListener(this);

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
        tvDescription.setText(givenDataByPreviousActivity.getString(Constants.DESCRIPTION));

        destinationLatitude = givenDataByPreviousActivity.getDouble(Constants.LATITUDE);
        destinationLongitude = givenDataByPreviousActivity.getDouble(Constants.LONGITUDE);
        Picasso.with(this).load(givenDataByPreviousActivity.getString(Constants.DETAIL_URL)).into(imageView);
        Picasso.with(this).load(givenDataByPreviousActivity.getString(Constants.DETAIL_URL)).into(expandedImageView);

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

        switch (view.getId()) {
            case R.id.detailImage:
                try {
                    zoomImage(view);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.btnNavigate:
                if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, Constants.REQUEST_ENABLE_BT);
//            openGoogleMapsForNavigation(getApplicationContext());
                } else {
                    openGoogleMapsForNavigation(getApplicationContext());
                }
                break;
        }
    }

    private void zoomImage(final View thumbView) throws IOException {
        if (currentAnimator != null) {
            currentAnimator.cancel();
        }

        final ImageView expandedImageView = (ImageView) findViewById(R.id.expandedImage);


        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();
//        expandedImageView.setImageBitmap(Picasso.with(this).load(givenDataByPreviousActivity.getString(Constants.DETAIL_URL)).get());

        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.container).getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);


        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {

            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }


        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);


        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left,
                        finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top,
                        finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                currentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                currentAnimator = null;
            }
        });
        set.start();
        currentAnimator = set;


        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentAnimator != null) {
                    currentAnimator.cancel();
                }
                AnimatorSet set = new AnimatorSet();
                set
                        .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView, View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView, View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        currentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        currentAnimator = null;
                    }
                });
                set.start();
                currentAnimator = set;
            }
        });
    }
}



