package com.webonise.malladvertisementproject;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import utils.Constants;

/**
 * Created by webonise on 10/9/15.
 */
public class SplashActivity extends Activity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        showProcessDialog();
        JsonObjectRequest jsonObjectRequest = getJsonObjectRequest();
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void showProcessDialog() {
        progressDialog = new ProgressDialog(SplashActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(true);
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setProgress(0);
        progressDialog.show();
    }

    private JsonObjectRequest getJsonObjectRequest() {
        return new JsonObjectRequest(Constants.URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {


                if (!isMyServiceRunning(GeoFenceMonitoringService.class)) {
                    Intent intent = new Intent(getBaseContext(), GeoFenceMonitoringService.class);
                    intent.putExtra(Constants.JSON_RESPONSE, jsonObject.toString());
//                    startService(intent);
                }

                //later put the jsonObject in intent
                Intent startMainScreen = new Intent(SplashActivity.this, OffersListActivity.class).putExtra(Constants.JSON_RESPONSE, jsonObject.toString());
                startActivity(startMainScreen);
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("VOLLEY ERROR", "");
                Toast.makeText(getBaseContext(), getString(R.string.internet_connectivity), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
//                finish();
            }
        });
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.d("service is already running", "");
                return true;
            }
        }
        return false;
    }


}
