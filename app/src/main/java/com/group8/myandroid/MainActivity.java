package com.group8.myandroid;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.group8.myandroid.database.DatabaseHelper;
import com.group8.myandroid.database.DatabaseManager;
import com.group8.myandroid.database.Shop;
import com.group8.myandroid.database.Shops;
import com.group8.myandroid.global.EasyLogger;
import android.Manifest;
import android.content.pm.PackageManager;


/**
 * <h1>MainActivity</h1>
 * This class provides the main activity of the app.
 *
 * @see AppCompatActivity
 * @author rxxuzi
 * @version 1.0.0
 * @since 1.0.3
 */
public class MainActivity extends AppCompatActivity {

    EasyLogger logger = new EasyLogger(this, true);
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private LocationProvider locationProvider;
    public static final boolean LOAD = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseManager.dbCleanUp(this);

        // JsonからDBにデータをパース
        DatabaseManager dbManager = new DatabaseManager(this);

        if(LOAD){
            try {
                // Shopsデータのロード
                dbManager.loadShopsFromJson();
                dbManager.addShopsArray();
            } catch (Exception e) {
                logger.error(e);
            }
        }

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        debugLog();

        // LocationProvider の初期化
        locationProvider = new LocationProvider(this);

        // パーミッションがある場合のみ、位置情報を取得
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // 位置情報をログに出力
            Location currentLocation = locationProvider.getCurrentLocation();
            if (currentLocation != null) {
                logger.debug("Latitude: " + currentLocation.getLatitude());
                logger.debug("Longitude: " + currentLocation.getLongitude());
            }
        }

        Location currentLocation = locationProvider.getCurrentLocation();
        if (currentLocation != null) {
            double latitude = currentLocation.getLatitude();
            double longitude = currentLocation.getLongitude();
            logger.debug("Latitude: " + latitude + ", Longitude: " + longitude);
        } else {
            logger.debug("Location is null");
        }

//        //位置情報を取得
//        locationProvider = new LocationProvider(this);
//        requestLocationPermission();

        // RecyclerViewのインスタンスを取得
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // LayoutManagerをセット（ここではLinearLayoutManagerを使用）
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ShopAdapterを作成し、RecyclerViewにセット
        ShopAdapter shopAdapter = new ShopAdapter(Shops.shops, this);
        recyclerView.setAdapter(shopAdapter);
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            locationProvider.startLocationUpdates();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                locationProvider.startLocationUpdates();
            } else {
                // Permission denied.
                // Handle the situation when user denies the permission.
            }
        }
    }

    private void debugLog() {
        for (Shop shop : Shops.shops) {
            logger.debug(shop.toString());
        }

        logger.debug("Shops.shops.size() = " + Shops.shops.size());
    }
}