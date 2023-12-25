package com.example.fxtest_02.entities;

public class Product {
    private long ID_product;
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    public Product(long ID_product,String name, double price, int quantity) {
        this(name, price, quantity);
        this.ID_product = ID_product;
    }

    public Product() {

    }

    public long getID_product() {
        return ID_product;
    }

    public void setID_product(long ID_product) {
        this.ID_product = ID_product;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return ID_product +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity;
    }
}
