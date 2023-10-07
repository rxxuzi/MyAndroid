package com.group8.myandroid;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.group8.myandroid.database.DatabaseHelper;
import com.group8.myandroid.database.DatabaseManager;
import com.group8.myandroid.database.Shop;
import com.group8.myandroid.database.Shops;
import com.group8.myandroid.global.EasyLogger;

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

        for (Shop shop : Shops.shops) {
            logger.debug(shop.toString());
        }

        logger.debug("Shops.shops.size() = " + Shops.shops.size());

        LocationProvider lp = new LocationProvider(this);

        // RecyclerViewのインスタンスを取得
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // LayoutManagerをセット（ここではLinearLayoutManagerを使用）
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ShopAdapterを作成し、RecyclerViewにセット
        ShopAdapter shopAdapter = new ShopAdapter(Shops.shops);
        recyclerView.setAdapter(shopAdapter);


    }
}