package com.example.mvp.favorites;

import com.example.mvp.model.Product;
import java.util.List;


public interface FavoriteView {

        void showProducts(List<Product> products);
        void showEmptyState();
        void showProductDeleted(String productName);
        void showError(String message);

}
