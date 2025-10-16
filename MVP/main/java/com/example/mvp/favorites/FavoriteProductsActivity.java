package com.example.mvp.favorites;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mvp.R;
import com.example.mvp.adapter.FavoriteAdapter;
import com.example.mvp.local.ProductDAO;
import com.example.mvp.model.Product;
import com.example.mvp.adapter.ProductAdapter;
import java.util.ArrayList;
import java.util.List;

public class FavoriteProductsActivity extends AppCompatActivity implements FavoriteView {

    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;
    private FavoritePresenter presenter;
    private ProductDAO productDAO;
    private LinearLayout emptyStateLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);


        recyclerView = findViewById(R.id.fav_recycler);
        presenter = new FavoriteProductsPresenter(this, this);
        setupRecyclerView();

        presenter.loadFavoriteProducts();
    }


    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FavoriteAdapter(this, new ArrayList<>(),
                product -> presenter.deleteProduct(product));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showProducts(List<Product> products) {
        recyclerView.setVisibility(View.VISIBLE);
        //emptyStateLayout.setVisibility(View.GONE);

        adapter.updateData(products);
    }

    @Override
    public void showEmptyState() {
        recyclerView.setVisibility(View.GONE);
        //emptyStateLayout.setVisibility(View.VISIBLE);
        Toast.makeText(this, "No favorite products yet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProductDeleted(String productName) {
        Toast.makeText(this, productName + " removed from favorites", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.destroy();
        }
    }
}