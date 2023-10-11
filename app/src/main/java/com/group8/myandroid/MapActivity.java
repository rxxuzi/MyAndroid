package com.group8.myandroid;

import android.Manifest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private static final int MARKER_WIDTH = 100;
    private static final int MARKER_HEIGHT = 100;

    // マップの初期座標
    private double latitude  = 35.68121504195521;
    private double longitude = 139.76723861886026;

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        this.latitude = intent.getDoubleExtra("latitude", 0);
        this.longitude = intent.getDoubleExtra("longitude", 0);
        String shopName = intent.getStringExtra("shopName");


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


        // マップの初期位置とズームレベルを設定
        GeoPoint startPoint = new GeoPoint(latitude, longitude);
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


        // マップをセットアップする処理

        // オプション: ピンを立てる
        OverlayItem myLocationOverlayItem = new OverlayItem(shopName, "This is your selected shop", startPoint);
        Drawable originalMarker = this.getResources().getDrawable(R.drawable.marker);

        // サイズ変更
        Drawable resizedMarker = resize(originalMarker, MARKER_WIDTH, MARKER_HEIGHT);

        // リサイズされたマーカーとして設定する
        myLocationOverlayItem.setMarker(resizedMarker);


        List<OverlayItem> overlayItems = new ArrayList<>();
        overlayItems.add(myLocationOverlayItem);

        ItemizedIconOverlay<OverlayItem> locationOverlay;

        locationOverlay = new ItemizedIconOverlay<>(overlayItems,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return true;
                    }
                }, getApplicationContext());


        mapView.getOverlays().add(locationOverlay);


        //前の画面に遷移するボタン
        // ボタンにクリックリスナーをセット
        Button prevButton = findViewById(R.id.prevButton);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 現在のアクティビティを終了し、前のアクティビティに戻る
                finish();
            }
        });

        // 店舗名をTextViewに設定
        TextView tvShopName = findViewById(R.id.tvShopName);
        tvShopName.setText(shopName);

    }

    /**
     * Resize the given drawable.
     *
     * @param image    Original drawable to be resized.
     * @param width    Desired width of the resulting drawable.
     * @param height   Desired height of the resulting drawable.
     * @return         New resized Drawable instance.
     */
    private Drawable resize(Drawable image, int width, int height) {
        Bitmap b = ((BitmapDrawable) image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, width, height, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }


}

