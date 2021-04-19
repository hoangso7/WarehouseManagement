package com.midterm.proj.warehousemanagement.model;

public class Product {
    private int ID_Product;     // primary key
    private String name;
    private String unit;
    private int number;
    private int price;

    public Product(int ID_Product, String name, String unit, int number, int price) {
        this.ID_Product = ID_Product;
        this.name = name;
        this.unit = unit;
        this.number = number;
        this.price = price;
    }

    public int getID_Product() {
        return ID_Product;
    }

    public void setID_Product(int ID_Product) {
        this.ID_Product = ID_Product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
