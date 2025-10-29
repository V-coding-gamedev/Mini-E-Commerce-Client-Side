package com.example.minie_commerce.ui.viewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minie_commerce.R;

import org.w3c.dom.Text;

public class CartViewHolder extends RecyclerView.ViewHolder {

    public ImageView cartItemImage;
    public TextView cartItemName, cartItemPrice, cartItemQuantity;
    public Button increaseQuantityBtn, decreaseQuantityBtn;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        cartItemImage = itemView.findViewById(R.id.cart_item_image);
        cartItemName = itemView.findViewById(R.id.cart_item_name);
        cartItemPrice = itemView.findViewById(R.id.cart_item_price);
        cartItemQuantity = itemView.findViewById(R.id.cart_item_quantity);
        decreaseQuantityBtn = itemView.findViewById(R.id.btn_decrease);
        increaseQuantityBtn = itemView.findViewById(R.id.btn_increase);
    }
}
