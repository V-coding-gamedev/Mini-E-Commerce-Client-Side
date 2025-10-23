package com.example.minie_commerce.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minie_commerce.R;

public class ShoppingCartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        RecyclerView recyclerView = findViewById(R.id.shoppingCartRecyclerView);
        Toolbar toolbar = findViewById(R.id.shoppingCartToolbar);
        setSupportActionBar(toolbar);


    }
}
