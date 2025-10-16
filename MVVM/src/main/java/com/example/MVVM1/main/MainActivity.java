package com.example.MVVM1.main;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.MVVM1.view.favorites.FavoriteProductsActivity;
import com.example.MVVM1.R;
import com.example.MVVM1.view.allproducts.RemoteActivity;

public class MainActivity extends AppCompatActivity {
    Button databaseBtn , remoteBtn;
    private static final String TAG = " API_ERROR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseBtn = findViewById(R.id.dataButton);
        remoteBtn = findViewById(R.id.remoteButton);

        remoteBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, RemoteActivity.class);
            startActivity(intent);
        });

        databaseBtn.setOnClickListener(v ->{
            Intent intent = new Intent(this, FavoriteProductsActivity.class);
            startActivity(intent);
        });

    }
}