package com.example.minie_commerce.data.models;

import com.example.minie_commerce.ui.ProductDetailActivity;

public class CartItems {
    long id;
    Cart cart;
    Product product;
    long quantity;

    public CartItems() {
    }

    public CartItems(long id, Cart cart, Product product, long quantity) {
        this.id = id;
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
