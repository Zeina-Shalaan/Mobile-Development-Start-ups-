package com.example.mvp.local;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvp.R;
import com.example.mvp.adapter.ProductAdapter;
import com.example.mvp.local.ProductDAO;
import com.example.mvp.local.LocalDataSource;
import com.example.mvp.model.Product;

import java.util.ArrayList;
import java.util.List;

public class LocalActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    ProductDAO productLocal;
    private static final String TAG = " LocalActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_view);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productLocal = new LocalDataSource(this).getProductDao();

        //productAdapter = new ProductAdapter(this, new ArrayList<>(),false);
        productAdapter = new ProductAdapter(this, new ArrayList<>(), false,
                product -> {
                    // Delete product logic
                    new Thread(() -> productLocal.delete(product)).start();
                });

        recyclerView.setAdapter(productAdapter);

        productLocal.getAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productAdapter.updateData(products);
            }
        });



    }
}