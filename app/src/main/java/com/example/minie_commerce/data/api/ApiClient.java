package com.example.minie_commerce.data.api;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.minie_commerce.data.network.*;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;
    private static final DynamicAuthInterceptor authInterceptor = new DynamicAuthInterceptor();

    public static Retrofit getClient() {

        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(authInterceptor).build();

            // Khởi tạo Retrofit client để kết nối Android app với backend (Spring Boot)
            retrofit = new Retrofit.Builder()
                    // Đặt Base URL cho toàn bộ API.
                    // Lưu ý: khi chạy trên Android Emulator, "localhost" sẽ trỏ vào chính Emulator,
                    // nên phải dùng "10.0.2.2" để truy cập server chạy trên máy thật (PC).
                    .baseUrl("http://10.0.2.2:8080/") // backend Spring Boot (nếu chạy local)
                    .client(client)
                    // Thêm GsonConverterFactory để Retrofit tự động chuyển JSON <-> Object Java.
                    .addConverterFactory(GsonConverterFactory.create())
                    // Xây dựng đối tượng Retrofit hoàn chỉnh.
                    .build();
        }

        return retrofit;
    }

    // Gán JWT Token vào ngay sau khi đăng nhập, để mọi chức năng khác tự động kiểm tra token
    public static void updateToken(String token){
        authInterceptor.setToken(token);
    }
}

