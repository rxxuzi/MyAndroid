package com.group8.myandroid;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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

    EasyLogger logger = new EasyLogger("XYZ" , true);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // JsonからDBにデータをパース
        DatabaseManager dbManager = new DatabaseManager(this);

        try {
            dbManager.loadShopsFromJson();
            dbManager.addShopsArray();
        } catch (Exception e) {
            logger.error(e);
        }

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        for (Shop shop : Shops.shops) {
            logger.debug(shop.toString());
        }

    }
}