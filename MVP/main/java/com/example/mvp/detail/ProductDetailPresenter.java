package com.example.mvp.detail;


import android.content.Context;
import com.example.mvp.repository.ProductRepository;
import com.example.mvp.model.Product;

public class ProductDetailPresenter implements Presenter {
    private View view;
    private final ProductRepository repository;

    public ProductDetailPresenter(View view, Context context) {
        this.view = view;
        this.repository = ProductRepository.getInstance(context);
    }

    @Override
    public void loadProduct(int productId) {
        repository.getProductById(productId, new ProductRepository.ProductCallback() {
            @Override
            public void onSuccess(Product product) {
                if (view != null) {
                    if (product != null) {
                        view.showProduct(product);
                    } else {
                        view.showError("Product not found");
                    }
                }
            }

            @Override
            public void onError(String error) {
                if (view != null) {
                    view.showError("Failed to load product: " + error);
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
                    view.closeActivity();
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
