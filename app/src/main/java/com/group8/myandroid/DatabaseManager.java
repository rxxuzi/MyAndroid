package com.group8.myandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

public class DatabaseManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private final Context context;

    public DatabaseManager(Context context) {
        this.context = context;
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public long insertShop(String name, double latitude, double longitude,
                           double rating, String genre, String description,
                           String homepage, String sns, String domicile) {
        return dbHelper.insertShops(name, latitude, longitude, rating, genre,
                description, homepage, sns, domicile);
    }

    public void loadShopsFromJson() throws Exception {
        // JSONデータの読み込み
        InputStream is = context.getResources().openRawResource(R.raw.shops);
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        is.close();
        String json = new String(buffer, "UTF-8");

        // JSONデータのパース
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject shopObj = jsonArray.getJSONObject(i);
            JSONObject location = shopObj.getJSONObject("location");
            JSONObject info = shopObj.getJSONObject("info");

            insertShop(
                    shopObj.getString("name"),
                    location.getDouble("latitude"),
                    location.getDouble("longitude"),
                    shopObj.getDouble("rating"),
                    shopObj.getString("genre"),
                    info.getString("description"),
                    info.optString("homepage", null),  // optStringはnullを許容
                    info.optString("sns", null),
                    info.getString("domicile")
            );
        }
    }

}
