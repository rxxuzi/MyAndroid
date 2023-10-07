package com.group8.myandroid;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.SQLException;

/**
 * <h1>DatabaseHelper</h1>
 * Helps manage database creation, connection, and version management.
 * It creates the necessary tables upon initial run and provides methods
 * to perform database upgrade operations when needed.<br>
 * Unimplemented elements include <strong> UPDATE</strong> and <strong> DELETE</strong>.
 *
 * @version 1.0.2
 * @author rxxuzi
 * @since 1.0.2
 * @see SQLiteOpenHelper
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // データベース名とバージョン
    private static final String DATABASE_NAME = "shops.db";
    private static final int DATABASE_VERSION = 1;

    // テーブル名とカラム名
    public static final String TABLE_NAME = "restaurants";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_HOMEPAGE = "homepage";
    public static final String COLUMN_SNS = "sns";
    public static final String COLUMN_DOMICILE = "domicile";

    /**
     * Constructs a new DatabaseHelper using the given context.
     *
     * @param context the context to be used for database operations.
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This method
     * will set up the tables and initial configurations.
     *
     * @param db the database instance.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_LATITUDE + " REAL, " +
                COLUMN_LONGITUDE + " REAL, " +
                COLUMN_RATING + " REAL, " +
                COLUMN_GENRE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_HOMEPAGE + " TEXT, " +
                COLUMN_SNS + " TEXT, " +
                COLUMN_DOMICILE + " TEXT" +
                ")";
        db.execSQL(createTableQuery);
    }

    /**
     *
     * Called when the database needs to be upgraded. This method
     * @param db the database instance.
     * @param oldVersion the old version of the database.
     * @param newVersion the new version of the database.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
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
     *
     * @since 1.0.3
     */
    public long insertShops(String name, double latitude, double longitude,
                            double rating, String genre, String description,
                            String homepage, String sns, String domicile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, name);
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);
        values.put(COLUMN_RATING, rating);
        values.put(COLUMN_GENRE, genre);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_HOMEPAGE, homepage);
        values.put(COLUMN_SNS, sns);
        values.put(COLUMN_DOMICILE, domicile);

        long result = db.insert(TABLE_NAME, null, values);
        if (result == -1) {
            throw new SQLException("Failed to insert data");
        }
        return result;
    }

    /**
     * This method returns all shop records.
     * @since 1.0.2
     * @see Cursor
     * @return the cursor of all shop records.
     */
    public Cursor getAllShops() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

}
