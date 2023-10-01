package com.group8.myandroid;

import android.Manifest;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import org.osmdroid.config.Configuration;
import org.osmdroid.library.BuildConfig;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ユーザーエージェントの設定
        Configuration.getInstance().setUserAgentValue(getPackageName());

        setContentView(R.layout.activity_map);

        // 現在地ボタンのインスタンスを取得
        Button goToCurrentLocationButton = findViewById(R.id.goToCurrentLocationButton);

        // 現在地ボタンのクリックリスナーを設定
        goToCurrentLocationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 現在の位置情報を取得
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // パーミッションの確認
                    return;
                }
            }
        });

        // ここでマップの初期設定を行います
        MapView mapView = findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(false);
        mapView.setMultiTouchControls(true);

        // マップの初期位置とズームレベルを設定
        GeoPoint startPoint = new GeoPoint(35.6895, 139.6917); // 例: 東京
        mapView.getController().setZoom(15);
        mapView.getController().setCenter(startPoint);

    }

}

