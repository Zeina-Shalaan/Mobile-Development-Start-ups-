package com.example.MVVM1.view.favorites;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.MVVM1.R;
import com.example.MVVM1.view.favorites.databaseVM.LocalVMFactory;
import com.example.MVVM1.view.favorites.databaseVM.DatabaseViewModel;
import com.example.MVVM1.model.local.LocalDataSource;
import com.example.MVVM1.model.Product;
import com.example.MVVM1.model.remote.RemoteDataSource;
import com.example.MVVM1.model.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class FavoriteProductsActivity extends AppCompatActivity implements onDeleteClicked  {

    private FavoriteAdapter adapter;
    private DatabaseViewModel databaseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);


        LocalDataSource local = new LocalDataSource(getApplicationContext());
        RemoteDataSource remote = new RemoteDataSource();

        ProductRepository repo = new ProductRepository(local, remote);

        LocalVMFactory factory = new LocalVMFactory(repo);

        databaseViewModel = new ViewModelProvider(this, factory)
                .get(DatabaseViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.fav_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new FavoriteAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);

        databaseViewModel.databaseProduct.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                adapter.updateData(products);
                adapter.notifyDataSetChanged();
            }
        });


    }


    @Override
    public void deleteProduct(Product product) {
        databaseViewModel.deleteProduct(product);
    }
}
