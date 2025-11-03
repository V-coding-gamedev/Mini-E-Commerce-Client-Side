package com.example.minie_commerce.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minie_commerce.R;
import com.example.minie_commerce.data.api.ApiClient;
import com.example.minie_commerce.data.api.ProductApiService;
import com.example.minie_commerce.data.models.Product;
import com.example.minie_commerce.ui.adapter.ProductAdapter;
import com.example.minie_commerce.ui.listener.ProductItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ProductItemClickListener {

    private RecyclerView recyclerView;
    private List<Product> products = new ArrayList<>();
    private ProductApiService productApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupRecyclerView();
        fetchProducts();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        productApiService = ApiClient.getClient().create(ProductApiService.class);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void fetchProducts() {
        productApiService.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    products.clear();
                    products.addAll(response.body());
                    recyclerView.setAdapter(new ProductAdapter(MainActivity.this, products, MainActivity.this));
                } else {
                    logApiError(response);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.shoppingCart) {
            startActivity(new Intent(this, ShoppingCartActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        Product product = products.get(position);
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("name", product.getName());
        intent.putExtra("description", product.getDescription());
        intent.putExtra("price", product.getPrice());
        intent.putExtra("image", product.getImage_url());
        intent.putExtra("productId", product.getId());
        startActivity(intent);
    }
}
