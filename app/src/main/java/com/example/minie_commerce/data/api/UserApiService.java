package com.example.minie_commerce.data.api;

import com.example.minie_commerce.data.response.LoginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

// ApiService nên để là interface vì chỉ cần mô tả hợp đồng (contract) – tức là
// khai báo các phương thức (API endpoints) chứ không chứa logic.
// Retrofit sẽ tự động generate implementation để thực thi HTTP request.
// Điều này giúp code gọn, dễ bảo trì và dễ mock/test.
public interface UserApiService {
    @FormUrlEncoded // cho biết dữ liệu sẽ được gửi đi theo dạng: application/x-www-form-urlencoded
    @POST("log-in")
    Call<LoginResponse> login(@Field("username") String username,
                              @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> register(@Field("username") String username,
                                @Field("password") String password,
                                @Field("email") String email);

    @GET("findByUsername")
    Call<Long> findByUsername(@Query("username") String username);
}
