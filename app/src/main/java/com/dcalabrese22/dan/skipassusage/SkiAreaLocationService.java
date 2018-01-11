package com.dcalabrese22.dan.skipassusage;

import android.Manifest;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.dcalabrese22.dan.maxpassusage.LocationChangedReceiver;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class SkiAreaLocationService extends IntentService implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final long UPDATE_INTERVAL = 1000 * 60 * 45;
    public static final long FASTEST_UPDATE_INTERVAL = UPDATE_INTERVAL / 2;
    public static Boolean mIsRequestingUpdates;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;
    private PendingIntent mPendingIntent;

    public SkiAreaLocationService(String name) {
        super(name);
    }

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
        }else {
            Intent intent = new Intent(this, LocationChangedReceiver.class);
            intent.setAction(LocationChangedReceiver.INTENT_FILTER);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                    PENDING_INTENT_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(
                    mLocationRequest, pendingIntent);
        }
    }


    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
