package com.group8.myandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * <h1>DatabaseManager</h1>
 *
 * This class provides methods to manage the database operations
 * related to shops. It includes methods for inserting, retrieving,
 * and deleting shop records.
 * @author rxxuzi
 * @version 1.0.0
 * @since 1.0.3
 */
public class DatabaseManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private final Context context;

    /**
     * Constructs a new DatabaseManager using the given context.
     *
     * @param context the context to be used for database operations.
     */
    public DatabaseManager(Context context) {
        this.context = context;
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Inserts a new shop record into the database.
     *
     * @param name the name of the shop.
     * @param latitude the latitude of the shop's location.
     * @param longitude the longitude of the shop's location.
     * @param rating the rating of the shop.
     *                      (0.0 - 5.0)
     * @param genre the genre of the shop.
     *                      (e.g. "Japanese", "Chinese", "Korean", "Noodle")
     * @param description the description of the shop.
     *                      (e.g. "Japanese food", "Chinese food", "Korean food", "American food")
     * @param homepage the homepage of the shop.
     *                      (e.g. "http://www.example.com", null)
     * @param sns the social network of the shop.
     *                      (e.g. "http://www.example.com", null)
     * @param domicile the domicile of the shop.
     *                      (e.g. "Tokyo, Japan", "New York, USA")
     * @return the row ID of the newly inserted shop record.
     */
    public long insertShop(String name, double latitude, double longitude,
                           double rating, String genre, String description,
                           String homepage, String sns, String domicile) {
        return dbHelper.insertShops(name, latitude, longitude, rating, genre,
                description, homepage, sns, domicile);
    }

    /**
     * Retrieves the shop record with the given row ID.
     * @throws Exception if the shop record is not found.
     * @since 1.0.3
     */
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
