package com.example.MVVM1.model.local;

import android.content.Context;

import com.example.MVVM1.model.Product;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalDataSource {

    private final ProductDAO productDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor(); // ADD THIS

    public LocalDataSource(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.productDao = db.productDAO();
    }

    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }


    public void delete(Product item) {
        executor.execute(() -> productDao.delete(item));
    }

    public void insertProduct(Product product) {
        executor.execute(() -> productDao.insert(product));
    }
}

