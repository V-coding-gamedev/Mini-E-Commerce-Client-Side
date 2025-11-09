package com.example.minie_commerce.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minie_commerce.R;
import com.example.minie_commerce.data.api.ApiClient;
import com.example.minie_commerce.data.api.CartApiService;
import com.example.minie_commerce.data.api.OrderApiService;
import com.example.minie_commerce.data.models.CartItems;
import com.example.minie_commerce.ui.adapter.CartAdapter;
import com.example.minie_commerce.ui.base.BaseActivity;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingCartActivity extends BaseActivity {

    private CartApiService cartApiService;
    private OrderApiService orderApiService;
    private RecyclerView recyclerView;
    private Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        setupToolbar();
        initServices();
        initRecyclerView();
        loadUserId();

        if (userId != null) {
            loadCartItems();
            setupOrderButton();
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initServices() {
        cartApiService = ApiClient.getClient().create(CartApiService.class);
        orderApiService = ApiClient.getClient().create(OrderApiService.class);
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.shoppingCartRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadUserId() {
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String id = prefs.getString("userId", null);
        if (id != null) {
            try {
                userId = Long.parseLong(id);
            } catch (NumberFormatException e) {
                Log.e("USER_ID", "Invalid userId format", e);
            }
        }
    }

    private void setupOrderButton() {
        Button btnOrder = findViewById(R.id.orderBtn);
        btnOrder.setOnClickListener(v -> confirmOrder());
    }

    private void loadCartItems() {
        cartApiService.getCartItems(userId).enqueue(new Callback<List<CartItems>>() {
            @Override
            public void onResponse(Call<List<CartItems>> call, Response<List<CartItems>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    recyclerView.setAdapter(new CartAdapter(ShoppingCartActivity.this, response.body()));
                } else {
                    Toast.makeText(ShoppingCartActivity.this, "Your cart is empty", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CartItems>> call, Throwable t) {
                showApiError("Failed to load cart items", t);
            }
        });
    }

    private void confirmOrder() {
        orderApiService.getTotalOrderPriceAndConfirmOrder(userId).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Double totalPrice = response.body();
                    Toast.makeText(ShoppingCartActivity.this,
                            "Total price: " + String.format("%.2f", totalPrice),
                            Toast.LENGTH_SHORT).show();
                } else {
                    logApiError(response);
                }
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                showApiError("Failed to confirm order", t);
            }
        });
    }

    private void showApiError(String message, Throwable t) {
        Log.e("API_ERROR", message + ": " + t.getMessage(), t);
        Toast.makeText(this, "Server connection error", Toast.LENGTH_SHORT).show();
    }

    private void logApiError(Response<?> response) {
        try {
            Log.e("API_ERROR", "Code: " + response.code() + ", Message: " + response.message());
            if (response.errorBody() != null)
                Log.e("API_ERROR", "ErrorBody: " + response.errorBody().string());
        } catch (Exception e) {
            Log.e("API_ERROR", "Error reading errorBody", e);
        }
    }
}
