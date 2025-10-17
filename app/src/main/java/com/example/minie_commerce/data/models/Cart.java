package com.example.minie_commerce.data.models;

import java.time.LocalDateTime;

public class Cart {
    long id;

    User user;

    LocalDateTime created_at;

    String status;

    public Cart() {
    }

    public Cart(long id, User user, LocalDateTime created_at, String status) {
        this.id = id;
        this.user = user;
        this.created_at = created_at;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
