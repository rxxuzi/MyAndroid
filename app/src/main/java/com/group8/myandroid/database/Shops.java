package com.group8.myandroid.database;

import com.group8.myandroid.global.EasyLogger;

import java.text.Collator;
import java.util.*;

public class Shops {
    public static List<Shop> shops = new ArrayList<>();
    public static List<Shop> shops_ = new ArrayList<>(); //temporary list

    private static final EasyLogger logger  = new EasyLogger("shops", true);

    public static void refresh() {
        shops_.clear();
        shops_.addAll(shops);
    }

    //IDでソート
    public static void sortById() {
        shops_.sort(Comparator.comparingInt(Shop::getId));
    }

    //評価でソート
    public static void sortByRating() {
        shops_.sort(Comparator.comparingDouble(Shop::getRating));
    }

    //名前でソート
    public static void sortByName() {
        shops_.sort(Comparator.comparing(Shop::getName));
    }

    //名前でソート(日本語)
    public static void sortByNameInJp() {
        // 日本語のコンパレータを取得
        Collator collator = Collator.getInstance(Locale.JAPANESE);
        shops_.sort(collator);
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

    public static List<Shop> sortShops(String sortOption) {
        refresh();

        if ("Rating".equals(sortOption)) {
            Collections.sort(shops_, new Comparator<Shop>() {
                @Override
                public int compare(Shop o1, Shop o2) {
                    // 評価を基に降順でソート
                    return Double.compare(o2.getRating(), o1.getRating());
                }
            });
        } else if ("Name".equals(sortOption)) {
            Collections.sort(shops_, new Comparator<Shop>() {
                @Override
                public int compare(Shop o1, Shop o2) {
                    // 名前を基に昇順でソート
                    return o1.getName().compareTo(o2.getName());
                }
            });
        } else if ("評価順".equals(sortOption)){
            Collections.sort(shops_, new Comparator<Shop>() {
                @Override
                public int compare(Shop o1, Shop o2) {
                    // 評価を基に降順でソート
                    return Double.compare(o2.getRating(), o1.getRating());
                }
            });
        } else {
            Collections.sort(shops_, new Comparator<Shop>() {
                @Override
                public int compare(Shop o1, Shop o2) {
                    // 評価を基に降順でソート
                    return Double.compare(o2.getRating(), o1.getRating());
                }
            });
        }
        logger.debug(shops_);
        return shops_;
    }
}
