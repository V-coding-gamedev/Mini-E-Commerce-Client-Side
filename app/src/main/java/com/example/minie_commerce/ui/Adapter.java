package com.example.minie_commerce.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minie_commerce.R;
import com.example.minie_commerce.data.models.Product;

import java.util.List;

import com.bumptech.glide.Glide;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    Context context;
    List<Product> products;

    public Adapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho item trong CardView.
        // Tham số attachToRoot = false để chỉ tạo View từ XML mà KHÔNG gắn ngay vào parent.
        // RecyclerView và LayoutManager sẽ tự quản lý việc add View này vào parent.
        // Nếu để true, item_view sẽ bị add 2 lần vào parent -> gây lỗi
        // "The specified child already has a parent".
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.productImageView.getContext())
                .load(products.get(position).getImage_url()) // truyền String URL
                .into(holder.productImageView);
        holder.productNameView.setText(products.get(position).getName());
        holder.productPriceView.setText(products.get(position).getPrice().toString());
        holder.productDescriptionView.setText(products.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
