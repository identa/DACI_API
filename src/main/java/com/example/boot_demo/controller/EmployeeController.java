package com.example.boot_demo.controller;

import com.example.boot_demo.entity.EmployeeEntity;
import com.example.boot_demo.model.*;
import com.example.boot_demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/all")
    public ResponseEntity<ServiceResult> getAllEmployee(@RequestBody CustomerGetRequest request) {
        return new ResponseEntity<>(employeeService.getAllCustomer(request), HttpStatus.OK);
    }

    private EmployeeModel employeeModel(EmployeeEntity employeeEntity) {
        EmployeeModel model = new EmployeeModel(employeeEntity.getId(),
                employeeEntity.getFirstName(),
                employeeEntity.getLastName(),
                employeeEntity.getEmail(),
                employeeEntity.getStatus(),
                employeeEntity.isDeleted(),
                employeeEntity.getRole().getId());

        return model;
    }

    @PostMapping("/signin")
    public ResponseEntity<ServiceResult> login(@RequestBody EmployeeSignInRequest request) {
        return new ResponseEntity<>(employeeService.signInEmployee(request), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody EmployeeSignUpModel signUpModel) {
        return new ResponseEntity(employeeModel(employeeService.getSignUp(signUpModel)), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ServiceResult> update(@RequestBody EmployeeModel model) {
        return new ResponseEntity<>(employeeService.getUpdate(model), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ServiceResult> delete(@RequestBody DeleteEmployeeRequest request) {
        return new ResponseEntity<>(employeeService.getDelete(request.getId()), HttpStatus.OK);
    }

    @GetMapping("/product/searchByName")
    public ResponseEntity<ServiceResult> searchByName(@RequestBody ProductNameRequest request) {
        return new ResponseEntity<>(employeeService.searchProductByName(request), HttpStatus.OK);
    }

    @GetMapping("/product/searchByCat")
    public ResponseEntity<ServiceResult> searchByCat(@RequestBody ProductByCatRequest request) {
        return new ResponseEntity<>(employeeService.searchProductByCat(request), HttpStatus.OK);
    }

    @PostMapping("/order/create")
    public ResponseEntity<ServiceResult> createOrder(@RequestBody OrderCreateRequest request) {
        return new ResponseEntity<>(employeeService.createOrder(request), HttpStatus.CREATED);
    }

    @PutMapping("/order/delete")
    public ResponseEntity<ServiceResult> deleteOrder(@RequestBody OrderDeleteRequest request){
        return new ResponseEntity<>(employeeService.deleteOrder(request), HttpStatus.OK);
    }

    @PutMapping("/product/delete")
    public ResponseEntity<ServiceResult> deleteProduct(@RequestBody ProductDeleteRequest request){
        return new ResponseEntity<>(employeeService.deleteProduct(request), HttpStatus.OK);
    }
}
