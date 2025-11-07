package com.example.minie_commerce.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingCartActivity extends AppCompatActivity {

    CartApiService cartApiService;
    private OrderApiService orderApiService;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        recyclerView = findViewById(R.id.shoppingCartRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Toolbar toolbar = findViewById(R.id.shoppingCartToolbar);
        setSupportActionBar(toolbar);

        cartApiService = ApiClient.getClient().create(CartApiService.class);
        orderApiService = ApiClient.getClient().create(OrderApiService.class);

        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String userId = prefs.getString("userId", null);

        loadCartItems(Long.parseLong(userId));

        Button btnOrder = findViewById(R.id.orderBtn);
        btnOrder.setOnClickListener(v -> confirmOrder(Long.parseLong(userId)));
    }

    private void loadCartItems(Long userId) {
        cartApiService.getCartItems(userId).enqueue(new Callback<List<CartItems>>() {
            @Override
            public void onResponse(Call<List<CartItems>> call, Response<List<CartItems>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CartAdapter cartAdapter = new CartAdapter(ShoppingCartActivity.this, response.body());
                    recyclerView.setAdapter(cartAdapter);
                } else {
                    Toast.makeText(ShoppingCartActivity.this, "There are no items in your cart", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CartItems>> call, Throwable t) {
                Toast.makeText(ShoppingCartActivity.this, "Server connection error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void confirmOrder(Long userId){
        orderApiService.getTotalOrderPriceAndConfirmOrder(userId).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if (response.isSuccessful() && response.body() != null){
                    Double totalPrice = response.body();
//                    totalAmountTextView.setText(String.format("%.2f", totalPrice));
//                    grandTotalTextView.setText(String.format("%.2f", totalPrice));

                    Toast.makeText(ShoppingCartActivity.this,
                            "Total price: " + String.format("%.2f", totalPrice),
                            Toast.LENGTH_SHORT).show();

                } else {
                    logApiError(response);
                }
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                Log.e("API_ERROR", "Failure: " + t.getMessage(), t);
            }
        });
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
