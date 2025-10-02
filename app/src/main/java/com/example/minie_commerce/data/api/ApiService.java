package com.example.minie_commerce.data.api;

import com.example.minie_commerce.data.models.User;
import com.example.minie_commerce.data.request.LoginRequest;
import com.example.minie_commerce.data.response.LoginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("log-in")
    Call<LoginResponse> login(@Field("username") String username,
                              @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> register(@Field("username") String username,
                                @Field("password") String password,
                                @Field("email") String email);
}
