package com.example.minie_commerce.data.api;

import com.example.minie_commerce.data.models.Product;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;

public interface ProductApiService {
    @GET("products")
    Call<List<Product>> getAllProducts();
}
