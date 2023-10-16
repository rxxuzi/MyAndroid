package com.group8.myandroid.global;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import org.json.JSONException;

import java.io.InputStream;

/**
 * <h1>BasicDBManager Interface</h1>
 *
 * The BasicDBManager interface prescribes a set of operations for managing
 * the lifecycle and structure of a database, including creating tables,
 * upgrading the database, and obtaining readable/writable instances of the database.
 *
 * Implementing classes are expected to provide concrete implementations for
 * handling specific entity types (such as Shop, User, Book, etc.) and their
 * corresponding database schemas.
 *
 * <p>
 * Example Usage:
 * <pre>
 *     public class MyDBManager implements BasicDBManager {
 *         // Implement the methods...
 *     }
 * </pre>
 * </p>
 *
 * @author rxxuzi
 * @since 1.4.0
 */
public interface BasicDBManager {
    /**
     * Retrieve a writable instance of the database.
     *
     * @return Writable database instance.
     */
    SQLiteDatabase getWritableDatabase();

    /**
     * Retrieve a readable instance of the database.
     *
     * @return Readable database instance.
     */
    SQLiteDatabase getReadableDatabase();

    /**
     * Cleanup the database
     */
    void dbCleanUp();

    /**
     * Upgrade the database to a new version.
     *
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    void upgradeDatabase(int oldVersion, int newVersion);

    /**
     * Close the database.
     */
    void closeDatabase();
}
