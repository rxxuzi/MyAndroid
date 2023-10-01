package com.group8.myandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // マップ表示ボタンのインスタンスを取得
        Button mapButton = findViewById(R.id.mapButton);

        // ボタンのクリックリスナーを設定
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MapActivityへのインテントを作成
                Intent intent = new Intent(MainActivity.this, MapActivity.class);

                // MapActivityを開始
                startActivity(intent);
            }
        });
    }
}