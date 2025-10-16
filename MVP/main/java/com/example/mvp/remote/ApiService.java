package com.example.mvp.remote;

import com.example.mvp.model.Item;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("products/")
    Call<Item> getItems();


}
