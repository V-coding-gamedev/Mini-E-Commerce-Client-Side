package com.example.minie_commerce.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.minie_commerce.R;
import com.example.minie_commerce.data.models.Cart;
import com.example.minie_commerce.data.models.CartItems;
import com.example.minie_commerce.ui.viewHolder.CartViewHolder;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    Context context;
    private List<CartItems> cartItems;
    Cart cart;

    public CartAdapter(Context context, List<CartItems> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(context).inflate(R.layout.item_shopping_cart_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItems item = cartItems.get(position);
        holder.cartItemName.setText(item.getName());

        String price = "$" + item.getPrice();
        holder.cartItemPrice.setText(price);

        holder.cartItemQuantity.setText(String.valueOf(item.getQuantity()));

        Glide.with(holder.cartItemImage.getContext())
                    .load(item.getImageUrl())
                    .into(holder.cartItemImage);

        holder.decreaseQuantityBtn.setOnClickListener(v -> {
            long quantity = item.getQuantity();
            if (quantity > 1) {
                quantity--;
                item.setQuantity(quantity);
                holder.cartItemQuantity.setText(String.valueOf(quantity));
            }
        });

        holder.increaseQuantityBtn.setOnClickListener(v -> {
            long quantity = item.getQuantity();
            quantity++;
            item.setQuantity(quantity);
            holder.cartItemQuantity.setText(String.valueOf(quantity));
        });

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }
}
