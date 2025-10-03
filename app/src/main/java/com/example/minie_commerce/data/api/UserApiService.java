package com.example.minie_commerce.data.api;

import com.example.minie_commerce.data.response.LoginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

// ApiService nên để là interface vì chỉ cần mô tả hợp đồng (contract) – tức là
// khai báo các phương thức (API endpoints) chứ không chứa logic.
// Retrofit sẽ tự động generate implementation để thực thi HTTP request.
// Điều này giúp code gọn, dễ bảo trì và dễ mock/test.
public interface UserApiService {
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
