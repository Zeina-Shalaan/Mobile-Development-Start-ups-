package com.example.mvp.remote;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.example.mvp.remote.ApiService;
import com.example.mvp.remote.RetrofitClient;
import com.example.mvp.model.Item;
import com.example.mvp.model.Product;
import retrofit2.Call;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;


public class RemoteDataSource {
    private final ApiService apiService;
    private static final String TAG = "RemoteDataSource";

    public RemoteDataSource() {
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<List<Product>> getProducts() {
        MutableLiveData<List<Product>> mutableLiveData = new MutableLiveData<>();

        Call<Item> call = apiService.getItems();
        new Thread(() -> {
            try {
                Response<Item> response = call.execute();
                if (response.isSuccessful() && response.body() != null) {
                    mutableLiveData.postValue(response.body().products);
                    Log.d(TAG, "Products loaded: " + response.body().products.size());
                } else {
                    mutableLiveData.postValue(new ArrayList<>());
                    Log.e(TAG, "API Error: " + response.code());
                }
            } catch (Exception e) {
                mutableLiveData.postValue(new ArrayList<>());
                Log.e(TAG, "Network Error: " + e.getMessage());
            }
        }).start();

        return mutableLiveData;
    }
}
