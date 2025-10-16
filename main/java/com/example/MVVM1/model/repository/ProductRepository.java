package com.example.MVVM1.model.repository;

import com.example.MVVM1.model.local.LocalDataSource;
import com.example.MVVM1.model.remote.RemoteDataSource;
import com.example.MVVM1.model.Product;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* mvvm , handles local DB
, remote API ,
background execution
 */

public class ProductRepository {
    private static ProductRepository instance;
    private final LocalDataSource localDataSource;
    private final RemoteDataSource remoteDataSource;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();


    public ProductRepository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public static synchronized ProductRepository getInstance(LocalDataSource localDataSource,RemoteDataSource remoteDataSource) {
        if (instance == null)
            instance = new ProductRepository(localDataSource,remoteDataSource);
        return instance;
    }

    // local database
    public List<Product> getLocalProducts()
    {
        return localDataSource.getAllProducts();
    }

    public void saveProduct(Product product) {
        executor.execute(()->localDataSource.insertProduct(product));
    }


    public void deleteProduct(Product product)
    {
        executor.execute(()->{localDataSource.delete(product);});
    }


    // Save product to local database
    public List<Product> getRemoteProducts()
    {
        return remoteDataSource.getProducts();
    }


    // Delete product from local database
//    public void deleteProduct(Product product, RepositoryCallback callback) {
//        executor.execute(() -> {
//            try {
//                localDataSource.delete(product);
//                if (callback != null) {
//                    callback.onSuccess();
//                }
//            } catch (Exception e) {
//                if (callback != null) {
//                    callback.onError(e.getMessage());
//                }
//            }
//        });
//    }

    // Get single product by ID
//    public void getProductById(int id, ProductCallback callback) {
//        executor.execute(() -> {
//            try {
//                Product product = localDataSource.getProductDao().getProductById(id);
//                if (callback != null) {
//                    callback.onSuccess(product);
//                }
//            } catch (Exception e) {
//                if (callback != null) {
//                    callback.onError(e.getMessage());
//                }
//            }
//        });
//    }



}
