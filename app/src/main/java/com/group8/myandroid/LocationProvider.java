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
 * This class provides location updates.
 * @since 1.0.4
 * @author rxxuzi
 */
public class LocationProvider implements LocationListener {

    private LocationManager locationManager;
    private static final long MIN_TIME = 1000;  // 更新時間（ミリ秒）
    private static final float MIN_DISTANCE = 1; // 更新距離（メートル）

    public LocationProvider(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void startLocationUpdates() {
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME,
                    MIN_DISTANCE,
                    this
            );
        } catch (SecurityException e) {
            new EasyLogger().error(e);
        }
    }

    public void stopLocationUpdates() {
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("Location", "Latitude: " + location.getLatitude());
        Log.d("Location", "Longitude: " + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // 位置プロバイダの状態が変わった時の処理
    }

    @Override
    public void onProviderEnabled(String provider) {
        // 位置プロバイダが有効になった時の処理
    }

    @Override
    public void onProviderDisabled(String provider) {
        // 位置プロバイダが無効になった時の処理
    }
}

