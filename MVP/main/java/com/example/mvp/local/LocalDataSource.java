package com.example.mvp.local;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.mvp.model.Product;
import com.example.mvp.local.ProductDAO;
import com.example.mvp.local.AppDatabase;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalDataSource {

    private final ProductDAO productDao;
    private LiveData<List<Product>> allProducts;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public LocalDataSource(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.productDao = db.productDAO();
    }

    public ProductDAO getProductDao(){
        return productDao;
    }
    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }


    public void delete(Product item) {
        executor.execute(() -> productDao.delete(item));
    }

    public void insertProduct(Product product) {
        executor.execute(() -> productDao.insert(product));
    }
}

