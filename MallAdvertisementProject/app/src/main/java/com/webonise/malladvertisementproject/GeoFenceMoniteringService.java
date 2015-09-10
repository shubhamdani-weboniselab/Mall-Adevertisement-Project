package com.webonise.malladvertisementproject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by webonise on 10/9/15.
 */
public class GeoFenceMoniteringService extends Service{
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
