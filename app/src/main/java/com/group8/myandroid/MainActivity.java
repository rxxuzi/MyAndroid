package com.group8.myandroid;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // JsonからDBにデータをパース
        DatabaseManager dbManager = new DatabaseManager(this);

        try {
            dbManager.loadShopsFromJson();
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage());
        }

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // データの追加
        long id = dbHelper.insertShops("Sample shops", 35.68121504195521, 139.76723861886026,
                3.5, "Japanese", "Sample description",
                null, "twitter.com", "Tokyo, Japan");
        if (id != -1) {
            Log.d("MainActivity", "Inserted shops with ID: " + id);
        }

        // データの取得
        Cursor cursor = dbHelper.getAllShops();
        while (cursor.moveToNext()) {
            int nameColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
            if (nameColumnIndex != -1) {
                String name = cursor.getString(nameColumnIndex);
                Log.d("MainActivity", "Retrieved shops: " + name);
            } else {
                Log.e("MainActivity", "Name column not found in the result.");
            }
        }
        cursor.close();

        // マップ表示ボタンのインスタンスを取得
        Button mapButton = findViewById(R.id.mapButton);

        // ボタンのクリックリスナーを設定
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MapActivityへのインテントを作成
                Intent intent = new Intent(MainActivity.this, MapActivity.class);

                // MapActivityを開始
                startActivity(intent);
            }
        });
    }
}