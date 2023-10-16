package com.group8.myandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.group8.myandroid.database.Shop;
import org.jetbrains.annotations.NotNull;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
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

    private Shop shop_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        shop_ = (Shop) getIntent().getExtras().getSerializable("shop");

        // マップの初期座標
        double latitude = intent.getDoubleExtra("latitude", 0);
        double longitude = intent.getDoubleExtra("longitude", 0);
        String shopName = intent.getStringExtra("shopName");


        // ユーザーエージェントの設定
        Configuration.getInstance().setUserAgentValue(getPackageName());

        setContentView(R.layout.activity_map);

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
        Drawable resizedMarker = resize(originalMarker);

        // リサイズされたマーカーとして設定する
        myLocationOverlayItem.setMarker(resizedMarker);


        List<OverlayItem> overlayItems = new ArrayList<>();
        overlayItems.add(myLocationOverlayItem);

        ItemizedIconOverlay<OverlayItem> locationOverlay = getOverlayItemItemizedIconOverlay(overlayItems);


        mapView.getOverlays().add(locationOverlay);


        // INFO ボタンにリスナーをセット
        Button btnInfo = findViewById(R.id.btnInfo);
        btnInfo.setOnClickListener(v -> openInfoActivity());

        // 店舗名をTextViewに設定
        TextView tvShopName = findViewById(R.id.tvShopName);
        tvShopName.setText(shopName);

    }

    @NotNull
    private ItemizedIconOverlay<OverlayItem> getOverlayItemItemizedIconOverlay(List<OverlayItem> overlayItems) {
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
        return locationOverlay;
    }

    // 新しいアクティビティを開くメソッド
    private void openInfoActivity() {
        // オプション：店舗情報をIntentに追加（遷移先で使用する場合）
        Intent intent = new Intent(MapActivity.this, InfoActivity.class);
        intent.putExtra("shop", shop_);  // selectedShopは選択されたShopインスタンス
        startActivity(intent);
    }

    /**
     * Resize the given drawable.
     *
     * @param image Original drawable to be resized.
     * @return New resized Drawable instance.
     */
    private Drawable resize(Drawable image) {
        Bitmap b = ((BitmapDrawable) image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, MapActivity.MARKER_WIDTH, MapActivity.MARKER_HEIGHT, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }


}

