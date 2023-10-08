package com.group8.myandroid.database;

import com.group8.myandroid.global.EasyLogger;

import java.text.Collator;
import java.util.*;

public class Shops {
    public static List<Shop> shops = new ArrayList<>();
    public static List<Shop> shops_ = new ArrayList<>(); //temporary list

    public static void refresh() {
        shops_.clear();
        shops_.addAll(shops);
    }

    //IDでソート
    public static void sortById() {
        shops.sort(Comparator.comparingInt(Shop::getId));
    }

    //評価でソート
    public static void sortByRating() {
        shops.sort(Comparator.comparingDouble(Shop::getRating));
    }

    //名前でソート
    public static void sortByName() {
        shops.sort(Comparator.comparing(Shop::getName));
    }

    //名前でソート(日本語)
    public static void sortByNameInJp() {
        // 日本語のコンパレータを取得
        Collator collator = Collator.getInstance(Locale.JAPANESE);
        shops.sort(collator);
    }

    public static void findByKeyword(String keyword) {
        shops_.clear();
        for (Shop shop : shops) {
            if (shop.getName().contains(keyword)) {
                shops_.add(shop);
            }
        }
    }

    //デバッグ用
    public static void print() {
        EasyLogger easyLogger = new EasyLogger("Shops DB Debug", true);
        for (Shop shop : shops) {
            easyLogger.debug(shop);
        }
    }
}
