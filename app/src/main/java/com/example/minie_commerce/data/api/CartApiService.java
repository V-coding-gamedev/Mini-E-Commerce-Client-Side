package com.example.minie_commerce.data.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CartApiService {
    @FormUrlEncoded
    @POST("addToCart")
    Call<ResponseBody> addToCart(@Field("userId") long userId,
                                @Field("productId") long productId,
                                @Field("quantity") long quantity);
}
