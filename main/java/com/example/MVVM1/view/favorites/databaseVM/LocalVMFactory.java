package com.example.MVVM1.view.favorites.databaseVM;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.MVVM1.model.repository.ProductRepository;

public class LocalVMFactory implements ViewModelProvider.Factory {
        private final ProductRepository repo;

        public LocalVMFactory(ProductRepository repo) { this.repo = repo ;}

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(DatabaseViewModel.class)){
                return (T) new DatabaseViewModel(repo);
            }
            throw new IllegalArgumentException("unknown ViewModel class");
        }


}
