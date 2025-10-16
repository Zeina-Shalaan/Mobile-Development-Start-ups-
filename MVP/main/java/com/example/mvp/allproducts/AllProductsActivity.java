package com.example.mvp.allproducts;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mvp.R;
import com.example.mvp.model.Product;
import com.example.mvp.adapter.ProductAdapter;
import java.util.ArrayList;
import java.util.List;


public class AllProductsActivity extends AppCompatActivity implements AllProductsContractView {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private AllProductsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_view);

        initViews();
        setupRecyclerView();

        presenter = new com.example.mvp.allproducts.AllProductsPresenter(this, this);
        presenter.loadProducts();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(this, new ArrayList<>(), true,
                product -> presenter.saveProduct(product)); // Save callback
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showProducts(List<Product> products) {
        adapter.updateData(products);
    }

    @Override
    public void showLoading() {
        // Show loading indicator if you have one
        Toast.makeText(this, "Loading products...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        // Hide loading indicator
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProductSaved(String productName) {
        Toast.makeText(this, productName + " saved to favorites!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.destroy();
        }
    }


}