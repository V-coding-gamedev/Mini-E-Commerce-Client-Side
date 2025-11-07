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

//        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
//        String userId = prefs.getString("userId", null);

        // confirmOrderAndGetTotalPrice(Long.parseLong(userId));
    }

    private void initViews() {
        // recyclerView = findViewById(R.id.recyclerview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        orderApiService = ApiClient.getClient().create(OrderApiService.class);
        totalAmountTextView = findViewById(R.id.value_total_amount);
        grandTotalTextView = findViewById(R.id.value_grand_total);
    }

    public void confirmOrderAndGetTotalPrice(Long userId ) {
        orderApiService.getTotalOrderPriceAndConfirmOrder(userId).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if (response.isSuccessful() && response.body() != null){
                    Double totalPrice = response.body();
                    totalAmountTextView.setText(String.format("%.2f", totalPrice));
                    grandTotalTextView.setText(String.format("%.2f", totalPrice));

                    Toast.makeText(OrderDetailActivity.this,
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
