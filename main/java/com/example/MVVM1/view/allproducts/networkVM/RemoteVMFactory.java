package com.example.MVVM1.view.allproducts.networkVM;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.MVVM1.model.repository.ProductRepository;

public class RemoteVMFactory implements ViewModelProvider.Factory {

    private ProductRepository repo;

    public RemoteVMFactory(ProductRepository repo) { this.repo = repo ;}


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NetworkViewModel.class)){
            return (T) new NetworkViewModel(repo);
        }
        throw new IllegalArgumentException("unknown ViewModel class");
    }
}
