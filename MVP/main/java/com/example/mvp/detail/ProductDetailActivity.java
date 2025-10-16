package com.example.mvp.detail;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mvp.R;
import com.example.mvp.local.AppDatabase;
import com.example.mvp.model.Product;

import java.util.concurrent.Executors;

public class ProductDetailActivity extends AppCompatActivity {
    ImageView imageView;
    TextView titleView, descView, priceView;
    Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        imageView = findViewById(R.id.imageView);
        titleView = findViewById(R.id.titleView);
        descView = findViewById(R.id.descView);
        priceView = findViewById(R.id.priceView);
        deleteButton = findViewById(R.id.deleteButton);

        int productId = getIntent().getIntExtra("productId", -1);

        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            Product product = db.productDAO().getProductById(productId);

            runOnUiThread(() -> {
                if (product != null) {
                    titleView.setText(product.title);
                    descView.setText(product.description);
                    priceView.setText("$" + product.price);

                    Glide.with(this)
                            .load(product.getThumbnail())
                            .into(imageView);

                    deleteButton.setOnClickListener(v -> {
                        Executors.newSingleThreadExecutor().execute(() -> {
                            db.productDAO().delete(product);
                            runOnUiThread(() -> finish());
                        });
                    });
                }
            });
        });
    }
}

