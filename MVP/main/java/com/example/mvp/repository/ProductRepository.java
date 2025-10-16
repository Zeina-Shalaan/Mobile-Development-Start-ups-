package com.example.mvp.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.mvp.local.*;
import com.example.mvp.remote.RemoteDataSource;
import com.example.mvp.model.Product;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductRepository {
    private static ProductRepository instance;
    private final LocalDataSource localDataSource;
    private final RemoteDataSource remoteDataSource;
    private final ExecutorService executor;
    private final ProductDAO productDAO;

    private ProductRepository(Context context) {
        localDataSource = new LocalDataSource(context);
        remoteDataSource = new RemoteDataSource();
        executor = Executors.newSingleThreadExecutor();
        this.productDAO = localDataSource.getProductDao();
    }

    public static synchronized ProductRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ProductRepository(context.getApplicationContext());
        }
        return instance;
    }

    // Get all products from local database
    public LiveData<List<Product>> getLocalProducts() {
        return productDAO.getAllProducts();
    }

    // Get products from remote API
    public MutableLiveData<List<Product>> getRemoteProducts() {
        return remoteDataSource.getProducts();
    }

    // Save product to local database
    public void saveProduct(Product product, RepositoryCallback callback) {
        executor.execute(() -> {
            try {
                localDataSource.insertProduct(product);
                if (callback != null) {
                    callback.onSuccess();
                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.onError(e.getMessage());
                }
            }
        });
    }


    // Delete product from local database
    public void deleteProduct(Product product, RepositoryCallback callback) {
        executor.execute(() -> {
            try {
                localDataSource.delete(product);
                if (callback != null) {
                    callback.onSuccess();
                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.onError(e.getMessage());
                }
            }
        });
    }

    // Get single product by ID
    public void getProductById(int id, ProductCallback callback) {
        executor.execute(() -> {
            try {
                Product product = localDataSource.getProductDao().getProductById(id);
                if (callback != null) {
                    callback.onSuccess(product);
                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.onError(e.getMessage());
                }
            }
        });
    }

    public interface RepositoryCallback {
        void onSuccess();
        void onError(String error);
    }

    public interface ProductCallback {
        void onSuccess(Product product);
        void onError(String error);
    }



}
