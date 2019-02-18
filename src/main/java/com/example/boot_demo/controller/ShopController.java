package com.example.boot_demo.controller;

import com.example.boot_demo.entity.EmployeeEntity;
import com.example.boot_demo.entity.ProductEntity;
import com.example.boot_demo.model.*;
import com.example.boot_demo.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/shop")
public class ShopController {

    @Autowired
    ShopService shopService;

    @PostMapping("/signup")
    public ResponseEntity<ServiceResult> signupShop(@RequestBody ShopSignUpRequest request) {
        return new ResponseEntity<>(shopService.signupShop(request), HttpStatus.CREATED);
    }

    @GetMapping("/signin")
    public ResponseEntity<ServiceResult> signinShop(@RequestBody ShopSignInRequest request) {
        return new ResponseEntity<>(shopService.signinShop(request), HttpStatus.OK);
    }

    @PostMapping("/product/create")
    public ResponseEntity<ServiceResult> createProduct(@RequestBody ProductCreateModel model) {
        return new ResponseEntity<>(shopService.createProduct(model), HttpStatus.CREATED);
    }

    @PutMapping("/product/update")
    public ResponseEntity<ServiceResult> updateProduct(@RequestBody ProductUpdateModel model) {
        return new ResponseEntity<>(shopService.updateProduct(model), HttpStatus.OK);
    }

    @GetMapping("product/search")
    public ResponseEntity<ServiceResult> searchProduct(@RequestBody SearchProductRequest request) {
        return new ResponseEntity<>(shopService.showProduct(request), HttpStatus.OK);
    }

    @PutMapping("/order/update")
    public ResponseEntity<ServiceResult> updateOrder(@RequestBody OrderUpdateRequest request) {
        return new ResponseEntity<>(shopService.updateOrder(request), HttpStatus.OK);
    }

    @PutMapping("/orderDetail/update")
    public ResponseEntity<ServiceResult> updateOrderDetail(@RequestBody OrderDetailUpdateRequest request) {
        return new ResponseEntity<>(shopService.updateOrderDetail(request), HttpStatus.OK);
    }
}
