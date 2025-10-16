package com.example.mvp.main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mvp.favorites.FavoriteProductsActivity;
import com.example.mvp.local.LocalActivity;
import com.example.mvp.R;
import com.example.mvp.remote.RemoteActivity;
import com.example.mvp.model.Product;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private List<Product> modelList;
    Button databaseBtn , remoteBtn , favBtn;
    private static final String TAG = " API_ERROR";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseBtn = findViewById(R.id.dataButton);
        remoteBtn = findViewById(R.id.remoteButton);
        favBtn = findViewById(R.id.favButton);

        remoteBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, RemoteActivity.class);
            startActivity(intent);
        });

        databaseBtn.setOnClickListener(v ->{
            Intent intent = new Intent(this, LocalActivity.class);
            startActivity(intent);
        });

        favBtn.setOnClickListener( v-> {
            Intent intent = new Intent(this, FavoriteProductsActivity.class);
            startActivity(intent);
        });

    }
}