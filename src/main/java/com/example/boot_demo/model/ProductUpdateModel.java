package com.example.boot_demo.model;

public class ProductUpdateModel {
    private int id;

    private String name;

    private String status;

    private String description;

    private int quantity;

    private double originalPrice;

    private int discount;

    private int view;

    private int categoryID;

    private int shopID;

    public ProductUpdateModel() {
    }

    public ProductUpdateModel(int id, String name, String status, String description, int quantity, double originalPrice, int discount, int view, int categoryID, int shopID) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.description = description;
        this.quantity = quantity;
        this.originalPrice = originalPrice;
        this.discount = discount;
        this.view = view;
        this.categoryID = categoryID;
        this.shopID = shopID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }
}
