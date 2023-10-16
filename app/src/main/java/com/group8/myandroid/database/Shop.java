package com.group8.myandroid.database;

import com.group8.myandroid.global.EasyLogger;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * <h1>Shop</h1>
 * Represents a shop with various attributes such as name, description, and address.
 * <p>
 * The Shop class captures all the details related to a specific shop. It implements the Serializable interface
 * which means its instances can be converted to a byte stream and back, allowing a Shop object to be easily saved
 * and restored, such as when passing between activities using Intents.
 * </p>
 *
 * @author rxxuzi
 */
public class Shop implements Serializable {
    private final int id;
    private String name;
    private final double latitude;
    private final double longitude;
    private final double rating;
    private final String genre;
    private final String description;
    private final String homepage;
    private final String sns;
    private final String domicile;

    // コンストラクタ
    public Shop(int id, String name, double latitude, double longitude, double rating,
                String genre, String description, String homepage, String sns, String domicile) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
        this.genre = genre;
        this.description = description;
        this.homepage = homepage;
        this.sns = sns;
        this.domicile = domicile;
    }

    // Getters and Setters are omitted for brevity...
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getRating() {
        return rating;
    }

    public String getGenre() {
        return genre;
    }

    public double getDistance(double lat, double lon) {
        return Math.sqrt(Math.pow(lat - latitude, 2) + Math.pow(lon - longitude, 2));
    }

    public String getDescription() {
        return description;
    }

    /**
     * Get website or SNS
     * If both website and sns are present, the website is returned first.
     * If homepage is null, return sns
     * If homepage and sns are null, return "None"
     * @return sns or homepage or "None"
     */
    public String getWebsite(){
        if (Objects.equals(homepage, "null") && Objects.equals(sns, "null")) {
            return "None";
        }else if (Objects.equals(homepage, "null")) {
            return sns;
        }
        return homepage;
    }

    public String getDomicile() {
        return domicile;
    }

    // Override toString() for debugging purposes

    @NotNull
    @Override
    public String toString() {
        return "id: " + id +
                ", name: " + name +
                ", [lat: " + latitude +
                ", long: " + longitude + "]" +
                ", rating: " + rating +
                ", genre: " + genre;
    }

}
