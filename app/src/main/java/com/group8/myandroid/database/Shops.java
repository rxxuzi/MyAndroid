package com.group8.myandroid.database;

import com.group8.myandroid.LocationProvider;
import com.group8.myandroid.global.EasyLogger;

import java.text.Collator;
import java.util.*;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * <h1>Shops</h1>
 * This class provides {@link Shop} related lists and methods such as sorting and filtering.
 * <p>
 *     <li>shops: Existing shops list</li>
 *     <li>shops_: Filtered and/or sorted shops list</li>
 *     <li>sortOp_: Last used sort option</li>
 *     <li>refresh(): Refresh shops list</li>
 *     <li>sortShops(int sortOption): Sort shops list</li>
 *     <li>filterShopsByGenre(String genre): Filter shops list by genre</li>
 *     <li>findByKeyword(String keyword): Find shops by keyword</li>
 *     <li>sortShops(): Sort shops list based on last used sort option</li>
 * </p>
 *
 * @see Shop
 * @author rxxuzi
 * @since 1.3.2
 *
 */
public class Shops {
    // 既存のリスト (更新不可能)
    public static final List<Shop> shops = new ArrayList<>();

    // フィルタリングおよび/またはソートされたショップリスト
    public static List<Shop> shops_ = new ArrayList<>(shops);

    private static int sortOp_ = -1;

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
        Collections.reverse(shops_);
    }

    static EasyLogger el = new EasyLogger("Shops");
    //距離でソート
    public static void sortByDistance() {
        double latitude,  longitude;
        try{
            latitude = LocationProvider.getCurrentLocation().getLatitude();
            longitude = LocationProvider.getCurrentLocation().getLongitude();
            el.debug("latitude: " + latitude + ", longitude: " + longitude);
        }catch (NullPointerException e){
            el.error(e);
            latitude = 35.72224704587137;
            longitude = 139.77610034729082;
        }
        double finalLatitude = latitude;
        double finalLongitude = longitude;
        shops_.sort(Comparator.comparingDouble(s -> s.getDistance(finalLatitude, finalLongitude)));
        // reverse sort shops_
        Collections.reverse(shops_);
    }

    @Deprecated
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

    //　文字列から対応するクラスのみをshops_に追加する
    public static void findByKeyword(String keyword) {
        shops_.clear();
        for (Shop shop : shops) {
            if (shop.getName().contains(keyword)) {
                shops_.add(shop);
            } else if (shop.getName().toLowerCase().contains(keyword.toLowerCase())){
                shops_.add(shop);
            }
        }
    }

    /**
     * <li>0: IDでソート</li>
     * <li>1: 名前でソート(日本語)</li>
     * <li>2: 評価でソート</li>
     * <li>3: 距離でソート</li>
     * @param sortOption 0 ~ 3
     */
    public static void sortShops(int sortOption) {
        //直近のソートオプションを保存
        sortOp_ = sortOption;
        sortShops();
    }

    public static void sortShops() {
        switch (sortOp_){
            case 1: sortByNameInJp(); break;
            case 2: sortByRating(); break;
            case 3: sortByDistance(); break;
            case 0:
            default: sortById(); break;
        }
    }

    public static void filterShopsByGenre(String genre) {
        if ("None".equals(genre)) {
            shops_ = new ArrayList<>(shops);  // No filtering, use all shops
        } else {
            shops_ = shops_.stream()
                    .filter(shop -> genre.equals(shop.getGenre()))
                    .collect(Collectors.toList());  // Filter by selected genre
        }
        sortShops();
    }

    /**
     * ジャンルを取得し、リスト化する。
     * またリスト化する際に、index : 0 に "None" を追加する。
     * @return genreList
     */
    public static List<String> getUniqueGenres() {
        Set<String> genresSet = new HashSet<>();
        for (Shop shop : shops) {
            genresSet.add(shop.getGenre());
        }
        List<String> genresList = new ArrayList<>(genresSet);

        // "None" を 0番目の要素に追加
        genresList.add(0, "None");

        return genresList;
    }

}
