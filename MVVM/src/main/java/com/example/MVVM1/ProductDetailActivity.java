package com.example.MVVM1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.MVVM1.view.allproducts.networkVM.RemoteVMFactory;
import com.example.MVVM1.view.favorites.databaseVM.DatabaseViewModel;
import com.example.MVVM1.model.local.LocalDataSource;
import com.example.MVVM1.model.remote.RemoteDataSource;
import com.example.MVVM1.model.repository.ProductRepository;

public class ProductDetailActivity extends AppCompatActivity {

    TextView tvTitle, tvDescription,priceView;
    ImageView imageView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        tvTitle = findViewById(R.id.titleView);
        tvDescription = findViewById(R.id.descView);
        imageView = findViewById(R.id.imageView);
        priceView = findViewById(R.id.priceView);

        LocalDataSource localDataSource = new LocalDataSource(getApplicationContext());   // <-- if you have a DB helper
        RemoteDataSource remoteDataSource = new RemoteDataSource(); // <-- if you have Retrofit service


        int productId = getIntent().getIntExtra("productId", -1);

        ProductRepository repo = new ProductRepository(localDataSource,remoteDataSource);
        RemoteVMFactory factory = new RemoteVMFactory(repo);

        DatabaseViewModel viewModel = new ViewModelProvider(this, factory)
                .get(DatabaseViewModel.class);


//        if (productId != -1)
//        {
//            viewModel.loadRemoteById(productId).observe(this, product -> {
//                tvTitle.setText(product.title);
//                tvDescription.setText(product.description);
//
//                String imageUrl = null;
//                if (product.getImages() != null && !product.getImages().isEmpty()) {
//                    imageUrl = product.getImages().get(0);
//                } else if (product.getThumbnail() != null) {
//                    imageUrl = product.getThumbnail();
//                }
//
//                if (imageUrl != null && !imageUrl.isEmpty()) {
//                    Glide.with(this)
//                            .load(imageUrl)
//                            .placeholder(android.R.drawable.ic_menu_gallery)
//                            .into(imageView);
//                } else
//                {
//                    imageView.setImageResource(android.R.drawable.ic_menu_gallery);
//                }
//            });

//        }
    }
}
