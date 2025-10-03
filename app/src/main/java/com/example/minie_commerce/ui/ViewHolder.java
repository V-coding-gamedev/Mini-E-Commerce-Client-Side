package com.example.minie_commerce.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minie_commerce.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    ImageView productImageView;
    TextView productNameView, productPriceView,  productDescriptionView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        productImageView = itemView.findViewById(R.id.productImage);
        productNameView = itemView.findViewById(R.id.productName);
        productPriceView = itemView.findViewById(R.id.productPrice);
        productDescriptionView = itemView.findViewById(R.id.productDescription);
    }
}
