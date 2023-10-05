package com.group8.myandroid.database;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Shops {
    public static List<Shop> shops = new ArrayList<>();

    //IDでソート
    public static void sortById(){
        shops.sort(Comparator.comparingInt(Shop::getId));
    }

    public static void sortByRating(){
        shops.sort(Comparator.comparingDouble(Shop::getRating));
    }
}
