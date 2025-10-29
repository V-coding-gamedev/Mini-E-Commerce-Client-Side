package com.example.minie_commerce.data.api;

import com.example.minie_commerce.data.models.CartItems;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.*;

public interface CartApiService {
    @FormUrlEncoded
    @POST("addToCart")
    Call<ResponseBody> addToCart(@Field("userId") long userId,
                                 @Field("productId") long productId,
                                 @Field("quantity") long quantity);

    @GET("api/cart/{userId}")
    Call<List<CartItems>> getCartItems(@Path("userId") Long userId);
}
