package com.example.minie_commerce.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    TextView detailDesc, detailTitle, detailPrice;
    ImageView detailImage;
    Button addToCartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        detailTitle = findViewById(R.id.detailTitle);
        detailImage = findViewById(R.id.detailImage);
        detailPrice = findViewById(R.id.detailPrice);
        detailDesc = findViewById(R.id.detailDesc);
        addToCartBtn = findViewById(R.id.addToCartButton);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){

            detailTitle.setText(bundle.getString("name"));
            detailPrice.setText(String.valueOf(bundle.getFloat("price")));
            detailDesc.setText(bundle.getString("description"));

            String imageUrl = bundle.getString("image");
            Glide.with(this)
                    .load(imageUrl)
                    .into(detailImage);
        }

        addToCartBtn.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
            String jwtToken = prefs.getString("jwtToken", null);

            CartApiService cartApiService = ApiClient.getClient().create(CartApiService.class);

            long productId = bundle.getLong("productId");
            // Toast.makeText(ProductDetailActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
            cartApiService.addToCart(11, productId, 1 ).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(ProductDetailActivity.this, "Add to cart success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(ProductDetailActivity.this, "Add to cart failed", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Kiểm tra xem item nào được nhấn dựa vào ID
        if (item.getItemId() == R.id.shoppingCart){
            startActivity(new Intent(ProductDetailActivity.this, ShoppingCartActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
