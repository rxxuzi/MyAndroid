package com.group8.myandroid.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.group8.myandroid.R;
import com.group8.myandroid.global.BasicDBManager;
import com.group8.myandroid.global.EasyLogger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
public class DatabaseManager implements BasicDBManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private final Context context;
    private static final boolean SAVE_ID = false;

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

    /**
     * Retrieve a writable instance of the database.
     *
     * @return Writable database instance.
     */
    @Override
    public SQLiteDatabase getWritableDatabase() {
        return dbHelper.getWritableDatabase();
    }

    /**
     * Retrieve a readable instance of the database.
     *
     * @return Readable database instance.
     */
    @Override
    public SQLiteDatabase getReadableDatabase() {
        return dbHelper.getReadableDatabase();
    }

    @SuppressWarnings("unused")
    public void dbCleanUp() {
        database.delete(DatabaseHelper.TABLE_NAME, null, null);
        closeDatabase();
    }

    /**
     * Upgrade the database to a new version.
     *
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void upgradeDatabase(int oldVersion, int newVersion) {
        // nothing
    }

    /**
     * Close the database.
     */
    @Override
    public void closeDatabase() {
        database.close();
    }

    public static void dbCleanUp(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        // テーブルから全てのデータを削除
        database.delete(DatabaseHelper.TABLE_NAME, null, null);

        if (!SAVE_ID){
            // sqlite_sequence テーブルから該当するテーブルのエントリを削除して、IDのカウンタをリセット
            String resetSql = "DELETE FROM sqlite_sequence WHERE name=?";
            database.execSQL(resetSql, new String[]{DatabaseHelper.TABLE_NAME});
        }

        database.close();
    }


    /**
     * Inserts a new shop record into the database.
     *
     * @param name        the name of the shop.
     * @param latitude    the latitude of the shop's location.
     * @param longitude   the longitude of the shop's location.
     * @param rating      the rating of the shop.
     *                    (0.0 - 5.0)
     * @param genre       the genre of the shop.
     *                    (e.g. "Japanese", "Chinese", "Korean", "Noodle")
     * @param description the description of the shop.
     *                    (e.g. "Japanese food", "Chinese food", "Korean food", "American food")
     * @param homepage    the homepage of the shop.
     *                    (e.g. "<a href="http://www.example.com">example.com</a>", null)
     * @param sns         the social network of the shop.
     *                    (e.g. "<a href="http://www.example.com">example.com</a>", null)
     * @param domicile    the domicile of the shop.
     *                    (e.g. "Tokyo, Japan", "New York, USA")
     */
    public void insertShop(String name, double latitude, double longitude,
                           double rating, String genre, String description,
                           String homepage, String sns, String domicile) {
        dbHelper.insertShops(name, latitude, longitude, rating, genre,
                description, homepage, sns, domicile);
    }

    /**
     * Retrieves the shop record with the given row ID.
     * @throws Exception if the shop record is not found.
     * @since 1.0.3
     */
    public void loadFromJson() throws Exception {
        // JSONデータの読み込み
        InputStream is = context.getResources().openRawResource(R.raw.shops);
        byte[] buffer = new byte[is.available()];
        int bytesRead = is.read(buffer);
        if (bytesRead != buffer.length) {
            Log.e(this.getClass().getName(), "loadFromJson: Failed to read JSON data");
        }
        is.close();
        String json = new String(buffer, StandardCharsets.UTF_8);

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
