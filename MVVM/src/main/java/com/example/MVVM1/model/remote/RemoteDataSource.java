package com.example.MVVM1.model.remote;

import static java.util.Collections.emptyList;

import android.util.Log;

import com.example.MVVM1.model.Item;
import com.example.MVVM1.model.Product;
import retrofit2.Call;
import retrofit2.Response;

import java.util.List;


public class RemoteDataSource {
    private final ApiService apiService;
    private static final String TAG = "RemoteDataSource";

    //NETWORK
    public RemoteDataSource() {
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    public List<Product> getProducts() {
        Call<Item> call = apiService.getItems();
            try {
                Response<Item> response = call.execute();
                if (response.isSuccessful() && response.body() != null) {
                    return response.body().products;
                } else {
                    return emptyList();
                }
            } catch (Exception e) {
                Log.e(TAG, "Network Error: " + e.getMessage());
            }

        return emptyList();
    }
}
