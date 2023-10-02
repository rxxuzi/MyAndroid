package com.group8.myandroid;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.SQLException;

/**
 * DatabaseHelper
 * @author rxxuzi
 * @version 1.0.2
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

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

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

    public Cursor getAllShops() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }

}
