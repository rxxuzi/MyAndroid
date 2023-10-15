package com.group8.myandroid.database;

import com.group8.myandroid.global.EasyLogger;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class Shop implements Serializable {
    private final int id;
    private String name;
    private final double[] location;
    private final double latitude;
    private final double longitude;
    private final double rating;
    private final String genre;
    private String description;
    private String homepage;
    private String sns;
    private String domicile;

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
        this.location = new double[]{latitude, longitude};
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


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

    public int getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double[] getLocation(){
        return location;
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

    public String getWebsite(){
        new EasyLogger(this).debug(homepage + ":" + sns);
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

}
