package com.example.minie_commerce.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.minie_commerce.R;
import com.example.minie_commerce.data.api.ApiClient;
import com.example.minie_commerce.data.api.UserApiService;
import com.example.minie_commerce.data.response.LoginResponse;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Tạo implementation của interface ApiService từ cấu hình Retrofit.
        // Retrofit sẽ tự generate code để gửi request/nhận response dựa trên định nghĩa @POST, @GET trong ApiService.
        UserApiService userApiService = ApiClient.getClient(null).create(UserApiService.class);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Gọi API login bằng enqueue() - khuyến nghị dùng so với execute():
            // - enqueue() sẽ thực hiện request ở background thread (bất đồng bộ),
            //   không làm chặn hoặc gây treo giao diện (UI thread).
            userApiService.login(username, password).enqueue(new Callback<LoginResponse>() {
                // Xử lý khi nhận được phản hồi từ server (HTTP 200, 400, 500...) <=> kết nối thành công
                // Call<LoginResponse> call: đại diện cho request API, trong TH này là login API
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    // login thành công và có dữ liệu trả về
                    if (response.isSuccessful() && response.body() != null) {
                        String token = response.body().getJwtToken();

                        // Lưu token vào SharedPreferences
                        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                        prefs.edit().putString("jwtToken", token).apply();

                        Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();

                        // chuyển sang MainActivity
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                }

                // Xử lý khi có lỗi kết nối, timeout, server không phản hồi...
                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}