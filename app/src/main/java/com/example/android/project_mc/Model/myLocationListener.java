package com.example.android.project_mc.Model;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

public class myLocationListener implements LocationListener {
    private Context context;

    public myLocationListener(Context context) {
        this.context = context;
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(context, location.getLatitude() + "," + location.getLongitude(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(context, "GPS Enabled", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(context, "GPS Disabled", Toast.LENGTH_LONG).show();
    }
}

