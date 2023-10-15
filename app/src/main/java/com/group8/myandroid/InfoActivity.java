package com.group8.myandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.group8.myandroid.database.Shop;

/**
 * InfoActivity displays detailed information about a selected shop.
 * It shows the name, description, and rating of the shop.
 *
 * @author rxxuzi
 */
public class InfoActivity extends AppCompatActivity {

    // UI Components
    private TextView tvName;
    private TextView tvDescription;
    private RatingBar rbRating;

    private TextView tvShopWebsite;
    private TextView tvShopAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // Initialize UI Components
        tvName = findViewById(R.id.tvShopName);
        tvDescription = findViewById(R.id.tvShopDescription);
        rbRating = findViewById(R.id.rbShopRating);
        tvShopWebsite = findViewById(R.id.tvShopWebsite);
        tvShopAddress = findViewById(R.id.tvShopAddress);

        // Get Data from Intent
        Intent intent = getIntent();

        // Obtaining a `Shop` object from an Intent
        Shop shop = (Shop) intent.getSerializableExtra("shop");

        // Set Data to UI Components
        tvName.setText(shop.getName());
        rbRating.setRating((float) shop.getRating());
        tvDescription.setText(shop.getDescription());
        tvShopWebsite.setText(shop.getWebsite());
        tvShopAddress.setText(shop.getDomicile());
    }
}
