package com.example.mvp.favorites;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.example.mvp.repository.ProductRepository;
import com.example.mvp.model.Product;
import java.util.List;

public class FavoriteProductsPresenter implements FavoritePresenter {
        private FavoriteView view;
        private final ProductRepository repository;

        public FavoriteProductsPresenter(FavoriteView view, Context context) {
            this.view = view;
            this.repository = ProductRepository.getInstance(context);
        }

        @Override
        public void loadFavoriteProducts() {
            repository.getLocalProducts().observe((LifecycleOwner) view,
                    products ->  {
                            if (view != null) {
                                if (products != null && !products.isEmpty()) {
                                    view.showProducts(products);
                                } else {
                                    view.showEmptyState();
                                }
                            }
                    });
        }

        @Override
        public void deleteProduct(Product product) {
            repository.deleteProduct(product, new ProductRepository.RepositoryCallback() {
                @Override
                public void onSuccess() {
                    if (view != null) {
                        view.showProductDeleted(product.title);
                    }
                }

                @Override
                public void onError(String error) {
                    if (view != null) {
                        view.showError("Failed to delete product: " + error);
                    }
                }
            });
        }

        @Override
        public void destroy() {
            view = null;
        }
}
