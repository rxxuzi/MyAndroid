package com.group8.myandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.group8.myandroid.database.Shop;

import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {
    private final List<Shop> shops;

    public ShopAdapter(List<Shop> shops) {
        this.shops = shops;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_item, parent, false);
        return new ShopViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        Shop shop = shops.get(position);
        holder.nameTextView.setText(shop.getName());
        // 他のビューへのデータバインディングもここで行います...
    }

    @Override
    public int getItemCount() {
        return shops.size();
    }

    public static class ShopViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.shopName);
            // 他のビューもここでバインドします...
        }
    }
}

