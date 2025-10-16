package com.example.MVVM1.view.favorites.databaseVM;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.MVVM1.model.Product;
import com.example.MVVM1.model.repository.ProductRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseViewModel extends ViewModel {
    private final ProductRepository repo;
    private final MutableLiveData<List<Product>> _databaseProduct = new MutableLiveData<>();
    public LiveData<List<Product>> databaseProduct = _databaseProduct;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public DatabaseViewModel(ProductRepository repo)
    {
            this.repo = repo;
    }

    {
        loadDatabaseProducts();
    }

    public void loadDatabaseProducts()
    {
        executor.execute(() -> _databaseProduct.postValue(repo.getLocalProducts()));
    }



    public void deleteProduct(Product product){
            repo.deleteProduct(product);
    }

}
