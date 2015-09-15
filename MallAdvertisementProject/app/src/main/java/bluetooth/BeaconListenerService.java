package bluetooth;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.webonise.malladvertisementproject.ProductDescriptionActivity;
import com.webonise.malladvertisementproject.R;

import java.util.List;

/**
 * Created by webonise on 11/9/15.
 */
public class BeaconListenerService extends Service {

    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mBluetoothLeScanner;
    private BluetoothManager bluetoothManager;

    private ScanSettings mScanSettings;
    private NotificationManager notificationManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Start Listening Beacon","");
        initializeViews();
        initializeBluetooth();
        setScanSettings();

        mBluetoothLeScanner.startScan(null, mScanSettings, mScanCallback);

    }

    @Override
    public void onDestroy() {
        mBluetoothLeScanner.stopScan(mScanCallback);
    }

    private void initializeViews() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }
    private void initializeBluetooth() {
        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

        mBluetoothAdapter = bluetoothManager.getAdapter();
        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
    }
    private void setScanSettings() {
        ScanSettings.Builder mBuilder = new ScanSettings.Builder();
        mBuilder.setReportDelay(0);
        mBuilder.setScanMode(ScanSettings.SCAN_MODE_LOW_POWER);
        mScanSettings = mBuilder.build();
    }
    protected ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            sendNotification(result.getDevice().getName(), result.getDevice().getAddress());
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Log.d("onScanFailed by error ", "" + errorCode);

        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
            Log.d(" onBatchScanResults: ", "LIst :: " + results);
        }

    };

    private void sendNotification(String detailOfTransition, String description) {


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(detailOfTransition)
                .setContentText(description).setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true);


        Intent intent = new Intent(this, ProductDescriptionActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(ProductDescriptionActivity.class);
        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        notificationManager.notify(1000, builder.build());
    }

}
