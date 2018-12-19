package com.example.lenovo.beaconsim;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton mAdvertiseButton;
    public int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         mAdvertiseButton = (ImageButton) findViewById( R.id.start);

             mAdvertiseButton.setOnClickListener(this);

        if( !BluetoothAdapter.getDefaultAdapter().isMultipleAdvertisementSupported() ) {
            Toast.makeText( this, "Multiple advertisement not supported", Toast.LENGTH_SHORT ).show();
            // mAdvertiseButton.setEnabled( false );
            // mDiscoverButton.setEnabled( false );

        }
        System.out.println( "went in");

    }
    AdvertiseCallback advertisingCallback = new AdvertiseCallback() {
        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            super.onStartSuccess(settingsInEffect);
        }

        @Override
        public void onStartFailure(int errorCode) {
            Log.e( "BLE", "Advertising onStartFailure: " + errorCode );
            super.onStartFailure(errorCode);
        }
    };

    private void advertise() {
        BluetoothLeAdvertiser advertiser = BluetoothAdapter.getDefaultAdapter().getBluetoothLeAdvertiser();

        AdvertiseSettings settings = new AdvertiseSettings.Builder()
                .setAdvertiseMode( AdvertiseSettings.ADVERTISE_MODE_LOW_POWER )
                .setTxPowerLevel( AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM )
                .setConnectable(false)
                .build();

        // ParcelUuid pUuid = new ParcelUuid( UUID.fromString( getString( R.string.ble_uuid ) ) );

        AdvertiseData data = new AdvertiseData.Builder()
                .setIncludeDeviceName( true )
                .build();


        advertiser.startAdvertising( settings, data, advertisingCallback );
        i++;
        Toast.makeText( this, "Started Advertising", Toast.LENGTH_SHORT ).show();
        mAdvertiseButton.setImageResource(R.mipmap.stop);
    }

    private void stop()
    {

        BluetoothLeAdvertiser advertiser = BluetoothAdapter.getDefaultAdapter().getBluetoothLeAdvertiser();
        i++;
        advertiser.stopAdvertising(advertisingCallback);
        Toast.makeText( this, "Stopped Advertising", Toast.LENGTH_SHORT ).show();
        mAdvertiseButton.setImageResource(R.mipmap.starr);
    }

    @Override
    public void onClick(View v) {
        //if( v.getId() == R.id.discover_btn ) {
         //   discover();
       /// } else
             if( v.getId() == R.id.start ) {
                 if(i%2==0)
                 {
                     System.out.println("Called advertise1");
                     advertise();
                 }
                 else
                 {
                     System.out.println("called stop");
                     stop();
                 }


        }
    }
}
