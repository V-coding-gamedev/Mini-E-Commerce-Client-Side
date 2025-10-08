package com.example.minie_commerce.ui;

import android.content.Intent;
import android.os.Bundle;

import com.example.minie_commerce.R;
import com.example.minie_commerce.data.api.ApiClient;
import com.example.minie_commerce.data.api.ProductApiService;
import com.example.minie_commerce.data.models.Product;
import com.example.minie_commerce.ui.adapter.ProductAdapter;
import com.example.minie_commerce.ui.adapter.ProductItemClickListener;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ProductItemClickListener {

     private Toolbar toolbar;

    List<Product> products = new ArrayList<Product>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        toolbar = findViewById(R.id.actionBar);
        setSupportActionBar(toolbar);

        // 2 cột (tức là 2 element / 1 hàng)
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        ProductApiService productApiService = ApiClient.getClient(null).create(ProductApiService.class);

        productApiService.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    products.clear();
                    products.addAll(response.body());

                    recyclerView.setAdapter(new ProductAdapter(MainActivity.this, products, MainActivity.this));
                }  else {
                    Log.e("API_ERROR", "Code: " + response.code() + ", Message: " + response.message());
                    try {
                        Log.e("API_ERROR", "ErrorBody: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("API_ERROR", "Failure: " + t.getMessage(), t);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);

        intent.putExtra("Name", products.get(position).getName());
        intent.putExtra("description", products.get(position).getDescription());
        intent.putExtra("price", products.get(position).getPrice());
        intent.putExtra("image", products.get(position).getImage_url()); // chỉ là nháp; cần kiểm tra kỹ lại

        startActivity(intent);

        }
}