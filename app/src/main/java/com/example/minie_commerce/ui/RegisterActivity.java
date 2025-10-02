package com.example.minie_commerce.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.minie_commerce.R;
import com.example.minie_commerce.data.api.ApiClient;
import com.example.minie_commerce.data.api.ApiService;
import com.example.minie_commerce.data.models.User;
import com.example.minie_commerce.data.response.LoginResponse;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText etUsername, etPassword, etEmail;

    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        btnRegister = findViewById(R.id.btnRegister);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if (!isValidPassword(password)) {
                Toast.makeText(this, "Password must be at least 8 characters, contain uppercase, lowercase and special character", Toast.LENGTH_LONG).show();
                return;
            }

            apiService.register(username, password, email).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Register successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    } else {
                        try {
                            String errorMsg = response.errorBody().string();
                            Toast.makeText(RegisterActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(RegisterActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Can not connect to API", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private boolean isValidPassword(String password) {
        // Regex: ít nhất 8 ký tự, có chữ thường, chữ hoa và ký tự đặc biệt
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!?.*()_]).{8,}$";
        return password.matches(passwordPattern);
    }
}
