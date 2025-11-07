package com.example.minie_commerce.data.api;

import com.example.minie_commerce.data.models.CartItems;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OrderApiService {
    @GET("confirmOrder/{userId}")
    Call<Double> getTotalOrderPriceAndConfirmOrder(@Path("userId") Long userId);
}
