package com.example.boot_demo.service;

import com.example.boot_demo.entity.*;
import com.example.boot_demo.model.*;
import com.example.boot_demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    private ShopSignInResponse signInResponse(ShopEntity entity) {
        ShopSignInResponse response = new ShopSignInResponse();
        response.setId(entity.getId());
        response.setEmail(entity.getEmail());
        response.setName(entity.getName());
        response.setAddress(entity.getAddress());
        response.setPhone(entity.getPhone());
        return response;
    }

    public ServiceResult createProduct(ProductCreateModel model) {
        ServiceResult result = new ServiceResult();

        if (productRepository.findById(model.getId()).isPresent()) {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage("Product existed");
        } else {
            CategoryEntity categoryEntity = categoryRepository.findById(model.getCategoryID()).orElse(null);
            ShopEntity shopEntity = shopRepository.findById(model.getShopID()).orElse(null);

            if (categoryEntity != null) {
                ProductEntity productEntity = new ProductEntity(model.getName(),
                        model.getStatus(),
                        model.isDeleted(),
                        model.getDescription(),
                        model.getOriginalPrice(),
                        categoryEntity,
                        shopEntity);

                productRepository.save(productEntity);
                model.setId(productEntity.getId());
                result.setMessage("Product is created successfully!");
                result.setData(model);
            } else {
                result.setStatus(ServiceResult.Status.FAILED);
                result.setMessage("This category does not exist");
            }
        }
        return result;
    }

    public ServiceResult updateProduct(ProductUpdateModel model) {
        ServiceResult result = new ServiceResult();
        ProductEntity updatedProduct = productRepository.findById(model.getId()).orElse(null);
        if (updatedProduct == null) {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage("Product does not exist");
        } else {
            CategoryEntity categoryEntity = categoryRepository.findById(model.getCategoryID()).orElse(null);
            ShopEntity shopEntity = shopRepository.findById(model.getShopID()).orElse(null);

            if (categoryEntity != null && shopEntity != null) {
                updatedProduct.setName(model.getName());
                updatedProduct.setCategory(categoryEntity);
                updatedProduct.setShop(shopEntity);
                updatedProduct.setOriginalPrice(model.getOriginalPrice());
                updatedProduct.setDiscount(model.getDiscount());
                updatedProduct.setDescription(model.getDescription());
                updatedProduct.setStatus(model.getStatus());
                updatedProduct.setQuantity(model.getQuantity());

                productRepository.save(updatedProduct);
                result.setMessage("Product is created successfully!");
                result.setData(model);
            } else {
                result.setStatus(ServiceResult.Status.FAILED);
                result.setMessage("This category does not exist");
            }
        }
        return result;
    }

    public ServiceResult showProduct(SearchProductRequest request) {
        ServiceResult result = new ServiceResult();
        ShopEntity shopEntity = shopRepository.findById(request.getId()).orElse(null);
        if (shopEntity == null) {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage("Product");
        } else {
            List<ProductEntity> productEntityList = productRepository.findByShop(shopEntity);
            result.setMessage("Search product successfully");
            result.setData(productEntityList);
        }
        return result;
    }

    public ServiceResult signupShop(ShopSignUpRequest request) {
        ServiceResult result = new ServiceResult();

        ShopEntity shopEntity = new ShopEntity(request.getEmail(),
                request.getPassword(),
                request.getName(),
                request.getPhone(),
                request.getAddress());

        shopRepository.save(shopEntity);

        result.setMessage("Shop is created successfully");
        result.setData(shopEntity);

        return result;
    }

    public ServiceResult signinShop(ShopSignInRequest request) {
        ServiceResult result = new ServiceResult();
        ShopEntity signedInShop = shopRepository.findByEmailAndPassword(request.getEmail(), request.getPassword()).orElse(null);

        if (signedInShop == null) {
            result.setMessage("Email or password is incorrect");
            result.setStatus(ServiceResult.Status.FAILED);
        } else {
            result.setMessage("Signin successfully");
            result.setData(signInResponse(signedInShop));
        }
        return result;
    }

    public ServiceResult updateOrder(OrderUpdateRequest request) {
        ServiceResult result = new ServiceResult();
        int index = updateOrderRequest(request.getId(), request.getStatus());
        if (index == 0) {
            result.setMessage("Order not found");
            result.setStatus(ServiceResult.Status.FAILED);
        } else if (index == 1) {
            result.setMessage("Nothing changes");
            result.setStatus(ServiceResult.Status.FAILED);
        } else result.setMessage("Update order successfully");

        return result;
    }

    public ServiceResult updateOrderDetail(OrderDetailUpdateRequest request) {
        ServiceResult result = new ServiceResult();
        int index = updateOrderDetailRequest(request.getId(), request.getQuantity());
        if (index == 0) {
            result.setMessage("Order detail not found");
            result.setStatus(ServiceResult.Status.FAILED);
        } else if (index == 1) {
            result.setMessage("Nothing changes");
            result.setStatus(ServiceResult.Status.FAILED);
        } else result.setMessage("Update order detail successfully");

        return result;
    }

    private int updateOrderDetailRequest(int id, int quantity) {
        OrderDetailEntity updatedOrderDetail = orderDetailRepository.findById(id).orElse(null);
        if (updatedOrderDetail != null) {
            if (updatedOrderDetail.getQuantity() == quantity) return 1;
            updatedOrderDetail.setQuantity(quantity);
            orderDetailRepository.save(updatedOrderDetail);
            return 2;
        } else return 0;
    }

    private int updateOrderRequest(int id, String status) {
        OrderEntity updatedOrder = orderRepository.findById(id).orElse(null);
        if (updatedOrder != null) {
            if (updatedOrder.getStatus().equals(status)) return 1;
            updatedOrder.setStatus(status);
            orderRepository.save(updatedOrder);
            return 2;
        } else return 0;
    }
}
