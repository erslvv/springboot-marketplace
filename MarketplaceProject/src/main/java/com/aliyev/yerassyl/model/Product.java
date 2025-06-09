package com.aliyev.yerassyl.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long product_id;


    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    public Product() {

    }


    public Product(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }


    public long getId() {
        return product_id;
    }

    public void setId(long id) {
        this.product_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product [id=" + product_id + ", name=" + name + ", desc=" + description  + "price=" + price + "]";
    }
}