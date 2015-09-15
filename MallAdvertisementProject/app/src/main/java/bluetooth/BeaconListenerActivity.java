package bluetooth;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.webonise.malladvertisementproject.DetailOfferViewActivity;
import com.webonise.malladvertisementproject.R;

import java.util.List;

/**
 * Created by webonise on 8/9/15.
 */
public class BeaconListenerActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mBluetoothLeScanner;
    private BluetoothManager bluetoothManager;
    private Button btnReceive;
    private Button btnSend;
    private ScanSettings mScanSettings;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.beacon_listener_activity);

        initializeViews();

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE Not Supported",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
        setScanSettings();

        mBluetoothLeScanner.startScan(null, mScanSettings, mScanCallback);

    }




    private void initializeViews() {
//        btnReceive = (Button) findViewById(R.id.btnStartReceiving);
//        btnSend = (Button) findViewById(R.id.btnStopReceiving);


        btnReceive.setOnClickListener(this);
        btnSend.setOnClickListener(this);

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
//            int mRssi = result.getRssi();
            sendNotification(result.getDevice().getName(), result.getDevice().getAddress());
//            tv.append("\n Device Name:" + result.getDevice().getName());
//            tv.append("\n Device Address :" + result.getDevice().getAddress());
//            tv.append("\n Type :" + result.getDevice().getType());
//            tv.append("\n distance :" + calculateDistance(result.getScanRecord().getTxPowerLevel(), mRssi) + "\n");
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.btnStartReceiving:

//                if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
//                    if (!mBluetoothAdapter.isDiscovering()) {
//                        mBluetoothLeScanner.startScan(null, mScanSettings, mScanCallback);
//                        Toast.makeText(this, getString(R.string.scanning_in_progress), Toast.LENGTH_LONG).show();
//                    }
//                }
//                break;
//            case R.id.btnStopReceiving:
//                mBluetoothLeScanner.stopScan(mScanCallback);
//                break;
        }

    }

    public float calculateDistance(int txPower, double rssi) {
        if (rssi == 0) {
            return -1.0f; // if we cannot determine accuracy, return -1.
        }
        double ratio = rssi * 1.0 / txPower;
        if (ratio < 1.0) {
            return (float) Math.pow(ratio, 10);
        } else {
            double accuracy = (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
            return (float) accuracy;
        }
    }

    private void sendNotification(String detailOfTransition, String description) {


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(detailOfTransition)
                .setContentText(description).setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true);

        Intent intent = new Intent(this, DetailOfferViewActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(DetailOfferViewActivity.class);
        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        notificationManager.notify(1000, builder.build());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mBluetoothLeScanner != null)
            mBluetoothLeScanner.stopScan(mScanCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }
}
