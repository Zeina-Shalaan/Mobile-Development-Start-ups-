package com.example.mvp.favorites;

import com.example.mvp.model.Product;

public interface FavoritePresenter {
    void loadFavoriteProducts();
    void deleteProduct(Product product);
    void destroy();
}
