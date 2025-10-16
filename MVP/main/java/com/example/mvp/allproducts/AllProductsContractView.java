package com.example.mvp.allproducts;

import com.example.mvp.model.Product;

import java.util.List;

public interface AllProductsContractView {
        void showProducts(List<Product> products);
        void showLoading();
        void hideLoading();
        void showError(String message);
        void showProductSaved(String ProductName);

}
