package com.example.mvp.detail;

import com.example.mvp.model.Product;

public interface Presenter {
    void loadProduct(int productId);
    void deleteProduct(Product product);
    void destroy();
}
