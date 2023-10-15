package com.group8.myandroid;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import com.group8.myandroid.global.EasyLogger;

/**
 * <h1>LocationProvider</h1>
 * Provides functionality to get the device's current location.
 * @see LocationListener
 * @see LocationManager
 * @author rxxuzi
 */
public class LocationProvider implements LocationListener {

    private LocationManager locationManager;
    private static Location currentLocation;
    private static final long UPDATE_TIME = 1000;  // 更新時間（ミリ秒）
    private static final float UPDATE_DISTANCE = 1; // 更新距離（メートル）

    public LocationProvider(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }


    /**
     * Starts location updates. Should be called when the application starts or when it is
     * brought to the foreground.
     */
    public void startLocationUpdates() {
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, // Use GPS for location updates.
                    UPDATE_TIME,        // Update interval in milliseconds.
                    UPDATE_DISTANCE,    // Minimum distance change for updates, in meters.
                    locationListener    // Listener for receiving updates.
            );
        } catch (SecurityException e) {
            Log.e("LocationProvider", "Location permissions not granted.", e);
        }
    }

    public void stopLocationUpdates() {
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        new EasyLogger(this).debug("onLocationChanged: " + location.getLatitude() + ", " + location.getLongitude());
    }

    /**
     * Retrieves the last known location of the device.
     *
     * @return the last known location, or null if no location is available.
     */
    public static Location getCurrentLocation() {
        return currentLocation;
    }

    // Create a LocationListener to receive location updates.
    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // Update the currentLocation variable whenever a new location is received.
            currentLocation = location;
            Log.d(this.getClass().getName(), "onLocationChanged: " + location.getLatitude() + ", " + location.getLongitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // Implement as needed.
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Implement as needed.
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Implement as needed.
        }
    };
}

