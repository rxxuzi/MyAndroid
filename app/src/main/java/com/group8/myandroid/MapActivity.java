package com.group8.myandroid;

import android.Manifest;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import org.osmdroid.config.Configuration;
import org.osmdroid.library.BuildConfig;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

/**
 * <h1>MapActivity</h1>
 * This class provides the map activity of the app.
 *
 * @see <a href="https://www.openstreetmap.org/">OpenStreetMap</a>
 * @see AppCompatActivity
 * @version 1.0.2
 * @since  1.0.1
 * @author rxxuzi
 *
 */
public class MapActivity extends AppCompatActivity {

    private static final double DEFAULT_ZOOM_VALUE = 17.5d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ユーザーエージェントの設定
        Configuration.getInstance().setUserAgentValue(getPackageName());

        setContentView(R.layout.activity_map);

        // 現在地ボタンのインスタンスを取得
        Button goToCurrentLocationButton = findViewById(R.id.goToCurrentLocationButton);

        // 現在地ボタンのクリックリスナーを設定
        goToCurrentLocationButton.setOnClickListener(v -> {
            // 現在の位置情報を取得
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // パーミッションの確認
                return;
            }
        });

        //マップの初期設定
        MapView mapView = findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(false);
        mapView.setMultiTouchControls(true);

        // マップの初期座標
        double aLatitude = 35.68121504195521,  aLongitude = 139.76723861886026;

        // マップの初期位置とズームレベルを設定
        GeoPoint startPoint = new GeoPoint(aLatitude,aLongitude);
        mapView.getController().setZoom(DEFAULT_ZOOM_VALUE);
        mapView.getController().setCenter(startPoint);

        // SeekBarによるZoom率設定
        SeekBar zoomSeekBar = findViewById(R.id.zoomSeekBar);
        zoomSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double zoomRate = ((double) progress / 2.5) + DEFAULT_ZOOM_VALUE;
                mapView.getController().setZoom(zoomRate);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Nothing to do
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Nothing to do
            }
        });





    }

}

