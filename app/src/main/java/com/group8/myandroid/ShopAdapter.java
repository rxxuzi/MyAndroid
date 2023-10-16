package com.group8.myandroid;

import android.annotation.SuppressLint;
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

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_item, parent, false);
        return new ViewHolder(itemView);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
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
            intent.putExtra("shop", currentShop);  // selectedShopは選択されたShopインスタンス
            context.startActivity(intent);
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return shopList.size();
    }

    /**
     * Provides a reference to the views for each data item.
     * Complex data items may need more than one view per item,
     * so the number of views must be provided.
     *
     * <p>In the implementation of this method, you should call
     * {@link #setHasStableIds(boolean)}
     */
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

    @SuppressLint("NotifyDataSetChanged")
    public void update(){
    	notifyDataSetChanged();
    }

    @SuppressWarnings("unused")
    public void addShop(Shop shop) {
        shopList.add(shop);
    }

    @SuppressWarnings("unused")
    public List<Shop> getShops() {
        return shopList;  // 返却値の型は List<Shop> だが、実際には ArrayList<Shop> を返す。
    }
}


