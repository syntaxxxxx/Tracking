package com.fiqri.ganteng.service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.fiqri.ganteng.model.Locations;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

@SuppressLint("Registered")
public class TrackingService extends Service {

    FirebaseAuth auth;
    String path = "locations";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private BroadcastReceiver stopReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            unregisterReceiver(this);
            stopSelf();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        auth = FirebaseAuth.getInstance();
        fetchUpdateLocation();
    }

    private void fetchUpdateLocation() {

        // should request device locations
        LocationRequest request = new LocationRequest();
        request.setInterval(1000);

        // get accurate location
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        // access permission location
        if (permission == PackageManager.PERMISSION_GRANTED) {

            // get last update device location
            client.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {

                    // set last location to database, so your app can showing last update device location
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
                    Location location = locationResult.getLastLocation();

                    // not null your location and save to database based on id user
                    if (location != null) {
                        ref.child(auth.getCurrentUser().getUid()).setValue(location);

                    } else {
                        Log.d("TAG", "Location Not Detected");
                    }
                }
            }, null);
        }
    }
}