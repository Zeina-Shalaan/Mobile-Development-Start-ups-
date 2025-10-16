package com.example.mvp.detail;

import com.example.mvp.model.Product;

public interface View {
        void showProduct(Product product);
        void showError(String message);
        void closeActivity();



}
