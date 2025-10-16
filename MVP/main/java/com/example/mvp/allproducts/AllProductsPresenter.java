package com.example.mvp.allproducts;


import android.content.Context;
import androidx.lifecycle.Observer;

import com.example.mvp.repository.ProductRepository;
import com.example.mvp.model.Product;
import java.util.List;

public class AllProductsPresenter implements AllProductsContractPresenter {
    private AllProductsContractView view;
    private final ProductRepository repository;

    public AllProductsPresenter(AllProductsActivity view, Context context) {
        this.view = (AllProductsContractView) view;
        this.repository = ProductRepository.getInstance(context);
    }

    @Override
    public void loadProducts() {
        if (view != null) {
            view.showLoading();
        }

        repository.getRemoteProducts().observe((androidx.lifecycle.LifecycleOwner) view,
                new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        if (view != null) {
                            view.hideLoading();
                            if (products != null && !products.isEmpty()) {
                                view.showProducts(products);
                            } else {
                                view.showError("No products found");
                            }
                        }
                    }
                });
    }

    @Override
    public void saveProduct(Product product) {
        repository.saveProduct(product, new ProductRepository.RepositoryCallback() {
            @Override
            public void onSuccess() {
                if (view != null) {
                    view.showProductSaved(product.title);
                }
            }

            @Override
            public void onError(String error) {
                if (view != null) {
                    view.showError("Failed to save product: " + error);
                }
            }
        });
    }

    @Override
    public void destroy() {
        view = null;
    }
}