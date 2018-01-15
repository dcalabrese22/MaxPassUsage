package com.dcalabrese22.dan.skipassusage;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Calendar;

public class SkiAreaLocationService extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final long UPDATE_INTERVAL = 1000 * 60 * 1;
    public static final long FASTEST_UPDATE_INTERVAL = UPDATE_INTERVAL / 2;
    public static Boolean mIsRequestingUpdates;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;
    private PendingIntent mPendingIntent;
    private Context mContext;


    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public static final int PENDING_INTENT_REQUEST_CODE = 13;

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            Intent intent = new Intent(this, LocationChangedReceiver.class);
            intent.setAction(LocationChangedReceiver.INTENT_FILTER);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                    PENDING_INTENT_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(
                    mLocationRequest, pendingIntent);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("On start command", "Called");
        buildGoogleApiClient();
        createLocationRequest();
        mContext = this;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "no permissions", Toast.LENGTH_SHORT).show();
        } else {
            LocationServices.getFusedLocationProviderClient(this).getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                Intent receiverIntent = new Intent(mContext,
                                        LocationChangedReceiver.class);
                                receiverIntent.putExtra("location", location);
                                receiverIntent.setAction(LocationChangedReceiver.INTENT_FILTER);
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext,
                                        PENDING_INTENT_REQUEST_CODE, receiverIntent,
                                        PendingIntent.FLAG_UPDATE_CURRENT);
                                Calendar calendar = Calendar.getInstance();
                                long interval = 60 * 60 * 2 * 1000;
                                AlarmManager alarmManager = (AlarmManager)
                                        getSystemService(Context.ALARM_SERVICE);
                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                                        calendar.getTimeInMillis(), interval, pendingIntent);
                            }
                        }
                    });
        }
        return START_STICKY;
    }


    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
