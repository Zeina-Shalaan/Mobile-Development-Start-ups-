package com.example.MVVM1.view.allproducts.networkVM;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.MVVM1.model.Product;
import com.example.MVVM1.model.repository.ProductRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkViewModel extends ViewModel {
    private final ProductRepository repo;
    private final MutableLiveData<List<Product>> _networkProduct = new MutableLiveData<>();
    public final LiveData<List<Product>> networkProduct = _networkProduct;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();



    public NetworkViewModel(ProductRepository repo){
        this.repo = repo;
    }
    public void getNetworkProducts() {
        executor.execute(() ->_networkProduct.postValue(repo.getRemoteProducts()));
    }

    public void insertProductLocal(Product product)
    {
        executor.execute(() -> repo.saveProduct(product));
    }

}
