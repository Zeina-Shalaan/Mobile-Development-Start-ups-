package com.example.MVVM1.view.allproducts;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MVVM1.R;
import com.example.MVVM1.model.remote.RemoteDataSource;
import com.example.MVVM1.view.allproducts.networkVM.NetworkViewModel;
import com.example.MVVM1.view.allproducts.networkVM.RemoteVMFactory;
import com.example.MVVM1.view.adapter.ProductAdapter;
import com.example.MVVM1.model.local.LocalDataSource;
import com.example.MVVM1.model.Product;
import com.example.MVVM1.model.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class RemoteActivity extends AppCompatActivity implements onFavClicked {

    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    NetworkViewModel networkViewModel;
    private static final String TAG = " API_ERROR";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_view);

        //recyclerview initialization
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productAdapter = new ProductAdapter(this, new ArrayList<>(),true);
        recyclerView.setAdapter(productAdapter);


        ProductRepository repo = ProductRepository.getInstance(
                new LocalDataSource(this),
                new RemoteDataSource()
        );

        RemoteVMFactory remoteVMFactory = new RemoteVMFactory(repo);
        networkViewModel = new ViewModelProvider(this,remoteVMFactory).get(NetworkViewModel.class);

        networkViewModel.networkProduct.observe(this, new Observer<List<Product>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Product> products) {
                productAdapter.updateData(products);
                productAdapter.notifyDataSetChanged();
            }
        });
        networkViewModel.getNetworkProducts();
    }


    public void addToFav(Product product) {
        networkViewModel.insertProductLocal(product);
    }
}