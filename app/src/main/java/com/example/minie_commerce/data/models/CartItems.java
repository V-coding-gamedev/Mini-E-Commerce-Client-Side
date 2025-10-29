package com.example.minie_commerce.data.models;

import com.example.minie_commerce.ui.ProductDetailActivity;
import com.google.gson.annotations.SerializedName;

public class CartItems {
    private Long productId;
    private String name;
    private double price;
    private String imageUrl;
    private long quantity;

    public CartItems() {
    }

    public CartItems(Long productId, String name, double price, String imageUrl, long quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
