package com.example.boot_demo.model;

import java.util.List;

public class OrderCreateRequest {

    private String status;

    private double totalPrice;

    private int employeeID;

    private int shopID;

    private List<OrderDetailCreateRequest> orderDetailCreateRequestList;

    public OrderCreateRequest() {
    }

    public OrderCreateRequest(String status, double totalPrice, int employeeID, int shopID, List<OrderDetailCreateRequest> orderDetailCreateRequestList) {
        this.status = status;
        this.totalPrice = totalPrice;
        this.employeeID = employeeID;
        this.shopID = shopID;
        this.orderDetailCreateRequestList = orderDetailCreateRequestList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public int getShopID() {
        return shopID;
    }

    public void setShopID(int shopID) {
        this.shopID = shopID;
    }

    public List<OrderDetailCreateRequest> getOrderDetailCreateRequestList() {
        return orderDetailCreateRequestList;
    }

    public void setOrderDetailCreateRequestList(List<OrderDetailCreateRequest> orderDetailCreateRequestList) {
        this.orderDetailCreateRequestList = orderDetailCreateRequestList;
    }
}
