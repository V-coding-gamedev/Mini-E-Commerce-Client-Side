package com.example.minie_commerce.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.minie_commerce.R;
import com.example.minie_commerce.data.api.ApiClient;
import com.example.minie_commerce.data.api.CartApiService;
import com.example.minie_commerce.ui.BaseActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends BaseActivity {
    private TextView detailTitle, detailDesc, detailPrice;
    private ImageView detailImage;
    private Button addToCartBtn;
    private long productId;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        setupToolbar();
        initViews();
        loadProductData();
        setupAddToCart();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initViews() {
        detailTitle = findViewById(R.id.detailTitle);
        detailDesc = findViewById(R.id.detailDesc);
        detailPrice = findViewById(R.id.detailPrice);
        detailImage = findViewById(R.id.detailImage);
        addToCartBtn = findViewById(R.id.addToCartButton);

        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        userId = prefs.getString("userId", null);
    }

    private void loadProductData() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) return;

        detailTitle.setText(extras.getString("name"));
        detailDesc.setText(extras.getString("description"));
        detailPrice.setText(String.valueOf(extras.getFloat("price")));
        productId = extras.getLong("productId");

        Glide.with(this)
                .load(extras.getString("image"))
                .into(detailImage);
    }

    private void setupAddToCart() {
        addToCartBtn.setOnClickListener(v -> {
            if (userId == null) {
                Toast.makeText(this, "Please log in first", Toast.LENGTH_SHORT).show();
                return;
            }

            CartApiService cartApiService = ApiClient.getClient().create(CartApiService.class);
            cartApiService.addToCart(Long.parseLong(userId), productId, 1)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Toast.makeText(ProductDetailActivity.this,
                                    response.isSuccessful() ? "Added to cart" : "Failed to add", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(ProductDetailActivity.this,
                                    "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
