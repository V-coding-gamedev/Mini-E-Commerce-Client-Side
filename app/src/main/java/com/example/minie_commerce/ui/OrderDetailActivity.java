package com.example.minie_commerce.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.minie_commerce.R;
import com.example.minie_commerce.data.api.ApiClient;
import com.example.minie_commerce.data.api.OrderApiService;
import com.example.minie_commerce.data.api.ProductApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {

    private OrderApiService orderApiService;

    private TextView totalAmountTextView, grandTotalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail_view);

        initViews();
    }

    private void initViews() {
        // recyclerView = findViewById(R.id.recyclerview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        orderApiService = ApiClient.getClient().create(OrderApiService.class);
        totalAmountTextView = findViewById(R.id.value_total_amount);
        grandTotalTextView = findViewById(R.id.value_grand_total);
    }
}
