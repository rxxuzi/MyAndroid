package com.group8.myandroid.global;

import android.database.Cursor;

/**
 * <h1>BasicDBHelper Interface</h1>
 *
 * The BasicDBHelper interface establishes a contract for managing database entities.
 * Implementations of this interface should provide specific logic for inserting,
 * updating, deleting, and retrieving entities in a database.
 *
 * <p>
 * Example Usage:
 * <pre>
 *     public class MyDBHelper implements BasicDBHelper {
 *         // Implement the methods...
 *     }
 * </pre>
 * </p>
 *
 * @author rxxuzi
 * @since 1.4.0
 */
public interface BasicDBHelper {

    int DATABASE_VERSION = 1;
    /**
     * Insert an entity into the database.
     *
     * @param name Name of the entity.
     * @param latitude Latitude of the entity.
     * @param longitude Longitude of the entity.
     * @return The row ID of the newly inserted entity record.
     */
    long insertEntity(String name, double latitude, double longitude);

    /**
     * Retrieve all entity records from the database.
     *
     * @return Cursor to all entity records.
     */
    Cursor getAllEntities();

    /**
     * Update an entity record in the database.
     *
     * @param id The ID of the entity to update.
     * @param name New name of the entity.
     * @param latitude New latitude of the entity.
     * @param longitude New longitude of the entity.
     * @return The number of rows affected.
     */
    int updateEntity(int id, String name, double latitude, double longitude);

    /**
     * Delete an entity record from the database.
     *
     * @param id The ID of the entity to delete.
     * @return The number of rows affected.
     */
    int deleteEntity(int id);

    /**
     * Cleanup the database (e.g., delete all records, reset ID counter, etc.)
     */
    void dbCleanUp();
}
