package com.group8.myandroid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.group8.myandroid.database.Shop;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>ShopAdapter</h1>
 * Adapter class for managing a list of shops in a RecyclerView.
 *
 * <p>This adapter is responsible for creating and binding ViewHolders
 * that represent items in a data set of Shop objects.
 *
 * @author rxxuzi
 */
public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private List<Shop> shopList;
    private final Context context;

    public ShopAdapter(List<Shop> shopList, Context context) {
        this.shopList = shopList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Shop currentShop = shopList.get(position);

        holder.shopNameTextView.setText(currentShop.getName());
        holder.btnMap.setOnClickListener(v -> {
            Intent intent = new Intent(context, MapActivity.class);
            //緯度経度を取得
            intent.putExtra("latitude", currentShop.getLatitude());
            intent.putExtra("longitude", currentShop.getLongitude());
            intent.putExtra("shopName", currentShop.getName());  // 店舗名を追加
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView shopNameTextView;
        Button btnMap;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopNameTextView = itemView.findViewById(R.id.shopName);
            btnMap = itemView.findViewById(R.id.btnMap);
        }
    }

    public void setShops(List<Shop> newShops) {
        this.shopList = newShops;
        update();
    }

    public void update(){
    	notifyDataSetChanged();
    }

    public void addShop(Shop shop) {
        shopList.add(shop);
    }

    public List<Shop> getShops() {
        return shopList;  // 返却値の型は List<Shop> だが、実際には ArrayList<Shop> を返す。
    }
}


