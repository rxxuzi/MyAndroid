package com.group8.myandroid.database;

import com.group8.myandroid.global.EasyLogger;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

public class Shops {
    public static List<Shop> shops = new ArrayList<>();
    public static List<Shop> shops_ = new ArrayList<>(); //temporary list
    public static List<Shop> genre = new ArrayList<>(); //temporary list

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
        shops_.sort((s1, s2) -> {
            // 名前を取得してCollatorを使って比較
            return collator.compare(s1.getName(), s2.getName());
        });

    }

    //距離でソート
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

    /**
     * <li>0: IDでソート</li>
     * <li>1: 名前でソート(日本語)</li>
     * <li>2: 評価でソート</li>
     * <li>3: 距離でソート</li>
     * @param sortOption 0 ~ 3
     */
    public static void sortedShops(int sortOption) {
        refresh();
        switch (sortOption){
            case 0: sortById(); break;
            case 1: sortByNameInJp(); break;
            case 2: sortByRating(); break;
            case 3: sortByRating(); break; //tmp
            default: sortById(); break;
        }
        logger.debug(shops_);
    }

    /**
     * フィルタリングされた店舗リストを取得します。
     *
     * @param genre フィルタリングしたいジャンル。
     * @return フィルタリングされた店舗リスト。
     */
    public static List<Shop> filteredShops(String genre) {
        return shops_.stream()
                .filter(shop -> shop.getGenre().equals(genre))
                .collect(Collectors.toList());
    }

    public static List<String> getUniqueGenres() {
        Set<String> genresSet = new HashSet<>();
        genresSet.add("None");
        for (Shop shop : shops) {
            genresSet.add(shop.getGenre());
        }
        List<String> genresList = new ArrayList<>(genresSet);

        // Add "None" at the beginning of the list
        genresList.add(0, "None");

        return genresList;
    }

}
