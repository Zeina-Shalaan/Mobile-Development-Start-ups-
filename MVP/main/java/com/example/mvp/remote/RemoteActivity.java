package com.example.mvp.remote;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvp.R;
import com.example.mvp.adapter.ProductAdapter;
import com.example.mvp.adapter.onFavClicked;
import com.example.mvp.local.ProductDAO;
import com.example.mvp.local.LocalDataSource;
import com.example.mvp.model.Product;

import java.util.ArrayList;

public class RemoteActivity extends AppCompatActivity implements onFavClicked {

    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    private Context context;
    ProductDAO productLocal;
    private static final String TAG = " API_ERROR";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_view);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productLocal = new LocalDataSource(this).getProductDao();
        //productAdapter = new ProductAdapter(this, new ArrayList<>(),true);
        productAdapter = new ProductAdapter(this, new ArrayList<>(), true,
                product -> {
                    // Save product logic
                    new Thread(() -> productLocal.insert(product)).start();
                });
        recyclerView.setAdapter(productAdapter);
        RemoteDataSource repository = new RemoteDataSource();
        repository.getProducts().observe(this, products -> {
            if (products != null && !products.isEmpty() ) {
                productAdapter.updateData(products);
            }
        });
    }

    @Override
    public void addToFav(Product product) {
        productLocal.insert(product);
    }
}