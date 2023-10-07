package com.group8.myandroid.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.group8.myandroid.R;
import com.group8.myandroid.global.EasyLogger;
import org.json.JSONArray;
import org.json.JSONObject;
import com.group8.myandroid.database.*;

import java.io.InputStream;

/**
 * <h1>DatabaseManager</h1>
 *
 * This class provides methods to manage the database operations
 * related to shops. It includes methods for inserting, retrieving,
 * and deleting shop records.
 *
 * @author rxxuzi
 * @version 1.2
 * @since 1.0.3
 */
public class DatabaseManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private final Context context;
    EasyLogger  logger = new EasyLogger("DBManger", true);

    /**
     * Constructs a new DatabaseManager using the given context.
     *
     * @param context the context to be used for database operations.
     */
    public DatabaseManager(Context context) {
        this.context = context;
        try  {
            dbHelper = new DatabaseHelper(context);
            database = dbHelper.getWritableDatabase();
        }catch (Exception e){
            logger.error(e);
        }
    }

    public void dbCleanUp() {
        database.delete(DatabaseHelper.TABLE_NAME, null, null);
        database.close();
    }

    // TODO クリーンアップした時にidが0からスタートしない問題を解決する
    public static void dbCleanUp(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(DatabaseHelper.TABLE_NAME, null, null);
        database.close();
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
                    info.optString("homepage", "null"),  // nullの場合、"null"という文字列を使用
                    info.optString("sns", "null"),  // nullの場合、"null"という文字列を使用
                    info.getString("domicile")
            );
        }

    }

    public void addShopsArray() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // データベースからすべての店舗情報を取得するクエリを実行
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // 各列のインデックスを取得
                int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID);
                int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
                int latitudeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LATITUDE);
                int longitudeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LONGITUDE);
                int ratingIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_RATING);
                int genreIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_GENRE);
                int descriptionIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION);
                int homepageIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_HOMEPAGE);
                int snsIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_SNS);
                int domicileIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_DOMICILE);

                // カーソルから値を取得
                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                double latitude = cursor.getDouble(latitudeIndex);
                double longitude = cursor.getDouble(longitudeIndex);
                double rating = cursor.getDouble(ratingIndex);
                String genre = cursor.getString(genreIndex);
                String description = cursor.getString(descriptionIndex);
                String homepage = cursor.getString(homepageIndex);
                String sns = cursor.getString(snsIndex);
                String domicile = cursor.getString(domicileIndex);

                // 新しいShopオブジェクトをインスタンス化し、リストに追加
                Shops.shops.add(new Shop(id, name, latitude, longitude, rating, genre, description, homepage, sns, domicile));
            } while (cursor.moveToNext());

            cursor.close();  // 忘れずにカーソルを閉じる
        }

        db.close();  // データベース接続を閉じる

    }


}
