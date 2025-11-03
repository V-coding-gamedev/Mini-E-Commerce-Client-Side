package com.example.minie_commerce.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minie_commerce.R;
import com.example.minie_commerce.data.models.Product;

import java.util.List;

import com.bumptech.glide.Glide;
import com.example.minie_commerce.ui.listener.ProductItemClickListener;
import com.example.minie_commerce.ui.viewHolder.ProductViewHolder;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    Context context;
    List<Product> products;
    final ProductItemClickListener productItemClickListener;

    public ProductAdapter(Context context, List<Product> products, ProductItemClickListener productItemClickListener) {
        this.context = context;
        this.products = products;
        this.productItemClickListener = productItemClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho item trong CardView.
        // Tham số attachToRoot = false để chỉ tạo View từ XML mà KHÔNG gắn ngay vào parent.
        // RecyclerView và LayoutManager sẽ tự quản lý việc add View này vào parent.
        // Nếu để true, item_view sẽ bị add 2 lần vào parent -> gây lỗi
        // "The specified child already has a parent".
        return new ProductViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_view, parent, false), productItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Glide.with(holder.productImageView.getContext())
                .load(products.get(position).getImage_url()) // truyền String URL
                .into(holder.productImageView);
        holder.productNameView.setText(products.get(position).getName());
        holder.productPriceView.setText(products.get(position).getPrice().toString());

        // holder.productId.setText(convertLongToString(products.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
