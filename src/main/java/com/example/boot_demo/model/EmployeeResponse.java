package com.example.boot_demo.model;

import java.util.List;

public class EmployeeResponse {
    private String responseCode;

    private List<EmployeeModel> employeeModelList;

    public EmployeeResponse() {
    }

    public EmployeeResponse(String responseCode, List<EmployeeModel> employeeModelList) {
        this.responseCode = responseCode;
        this.employeeModelList = employeeModelList;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public List<EmployeeModel> getEmployeeModelList() {
        return employeeModelList;
    }

    public void setEmployeeModelList(List<EmployeeModel> employeeModelList) {
        this.employeeModelList = employeeModelList;
    }
}
