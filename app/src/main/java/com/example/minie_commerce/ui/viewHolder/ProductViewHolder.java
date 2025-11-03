package com.example.minie_commerce.ui.viewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minie_commerce.R;
import com.example.minie_commerce.ui.listener.ProductItemClickListener;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    public ImageView productImageView;
    public TextView productNameView, productPriceView;

    public ProductViewHolder(@NonNull View itemView, ProductItemClickListener productItemClickListener) {
        super(itemView);
        productImageView = itemView.findViewById(R.id.productImage);
        productNameView = itemView.findViewById(R.id.productName);
        productPriceView = itemView.findViewById(R.id.productPrice);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productItemClickListener != null){
                    int position = getAbsoluteAdapterPosition();

                    if (position != RecyclerView.NO_POSITION){
                        productItemClickListener.onItemClick(position);
                    }
                }
            }
        });
    }
}
