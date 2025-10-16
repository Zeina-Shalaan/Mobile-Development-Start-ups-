package com.example.MVVM1.model.remote;

import com.example.MVVM1.model.Item;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("products/")
    Call<Item> getItems();


}
