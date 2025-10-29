package com.example.minie_commerce.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minie_commerce.R;
import com.example.minie_commerce.data.api.ApiClient;
import com.example.minie_commerce.data.api.CartApiService;
import com.example.minie_commerce.data.models.CartItems;
import com.example.minie_commerce.ui.adapter.CartAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingCartActivity extends AppCompatActivity {

    CartApiService cartApiService;
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

        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String userId = prefs.getString("userId", null);

        loadCartItems(Long.parseLong(userId));
    }

    private void loadCartItems(Long userId) {
        cartApiService.getCartItems(userId).enqueue(new Callback<List<CartItems>>() {
            @Override
            public void onResponse(Call<List<CartItems>> call, Response<List<CartItems>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CartAdapter cartAdapter = new CartAdapter(ShoppingCartActivity.this, response.body());
                    recyclerView.setAdapter(cartAdapter);
                } else {
                    Toast.makeText(ShoppingCartActivity.this, "Không có sản phẩm trong giỏ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CartItems>> call, Throwable t) {
                Toast.makeText(ShoppingCartActivity.this, "Lỗi kết nối server", Toast.LENGTH_SHORT).show();
            }


        });
    }
}
