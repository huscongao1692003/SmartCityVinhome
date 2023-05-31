package com.thanhnguyen.smartCity.model;

import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class Item {

    private Product product;
    private int quantity;



    public Product getProduct() {
        return product;
    }


    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Item() {
    }

    public Item(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

}