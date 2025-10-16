package com.example.mvp.allproducts;

import com.example.mvp.model.Product;

public interface AllProductsContractPresenter {
    void loadProducts();
    void saveProduct(Product product);
    void destroy();
}
