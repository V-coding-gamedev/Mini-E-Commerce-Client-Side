package com.example.minie_commerce.ui.base;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.minie_commerce.R;
import com.example.minie_commerce.ui.OrderDetailActivity;
import com.example.minie_commerce.ui.ShoppingCartActivity;

public abstract class BaseActivity extends AppCompatActivity {

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
        else if (item.getItemId() == R.id.orderedItems) {
            startActivity(new Intent(this, OrderDetailActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
