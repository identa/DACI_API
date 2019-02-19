package com.example.boot_demo.service;

import com.example.boot_demo.entity.*;
import com.example.boot_demo.model.*;
import com.example.boot_demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

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

    @Autowired
    JwtService jwtService;

    //--------------------------get all customer ------------------------------
    private CustomerGetResponse createCustomerGetResponse(EmployeeEntity entity) {
        CustomerGetResponse response = new CustomerGetResponse(entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getStatus());
        return response;
    }

    private List<CustomerGetResponse> createCustomerGetResponseList(boolean deleted, int roleID) {
        List<EmployeeEntity> customerList = employeeRepository.findByDeletedAndRoleId(deleted, roleID);
        List<CustomerGetResponse> responseList = new ArrayList<>();
        for (EmployeeEntity entity : customerList) {
            responseList.add(createCustomerGetResponse(entity));
        }
        return responseList;
    }

    public ServiceResult getAllCustomer(CustomerGetRequest request) {
        ServiceResult result = new ServiceResult();
        List<CustomerGetResponse> employeeEntityList = createCustomerGetResponseList(request.isDeleted(), request.getRoleID());
        if (employeeEntityList != null) {
            result.setMessage("Get all customers successfully");
            result.setData(employeeEntityList);
        } else {
            result.setMessage("Get all customers failed");
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    //--------------------------------------------------------------

    //---------------------get customer order -------------------------

//    public
    //-----------------------------------------------------------------

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

    private ProductByNameModel productByNameModel(ProductEntity productEntity) {
        ProductByNameModel model = new ProductByNameModel(productEntity.getId(),
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getOriginalPrice(),
                productEntity.getDiscount(),
                productEntity.getCategory().getId(),
                productEntity.getShop().getId());
        return model;
    }

    private List<ProductByNameModel> productByNameModelList(String name) {
        List<ProductEntity> productEntityList = productRepository.findByNameContaining(name);
        List<ProductByNameModel> modelList = new ArrayList<>();
        for (ProductEntity entity : productEntityList) {
            modelList.add(productByNameModel(entity));
        }
        return modelList;
    }

    private List<ProductByNameModel> productByCatModelList(int id) {
        List<ProductEntity> productEntityList = productRepository.findByCategoryId(id);
        List<ProductByNameModel> modelList = new ArrayList<>();
        for (ProductEntity entity : productEntityList) {
            modelList.add(productByNameModel(entity));
        }
        return modelList;
    }


//    public EmployeeEntity getLogin(String email, String password) {
//        return employeeRepository.findByEmailAndPassword(email, password);
//    }

    public ServiceResult signInEmployee(EmployeeSignInRequest request){
        ServiceResult result = new ServiceResult();
        EmployeeEntity employee = employeeRepository.findByEmailAndPassword(request.getEmail(), request.getPassword());
        if (employee != null){
            result.setMessage("Sign in successfully");
            result.setData(jwtService.generateTokenLogin(request.getEmail()));
        }else {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage("Email or password is incorrect");
        }
        return result;
    }

    public EmployeeEntity getSignUp(EmployeeSignUpModel signUpModel) {
        EmployeeEntity employeeEntity = new EmployeeEntity(signUpModel.getFirstName(),
                signUpModel.getLastName(),
                signUpModel.getEmail(),
                signUpModel.getPassword(),
                "KO",
                false,
                roleRepository.findById(1));
        return employeeRepository.save(employeeEntity);
    }

    public ServiceResult getDelete(int id) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity entity = employeeRepository.findById(id).orElse(null);
        if (entity != null) {
            employeeRepository.delete(entity);
            result.setMessage("Delete successfully!");
        } else {
            result.setMessage("Delete failed");
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    public ServiceResult getUpdate(EmployeeModel model) {
        EmployeeEntity employeeEntity = employeeRepository.findById(model.getId()).orElse(null);
        ServiceResult result = new ServiceResult();
        if (employeeEntity == null) {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage("Customer Not Found");
        } else {
            employeeEntity.setFirstName(model.getFirstName());
            employeeEntity.setLastName(model.getLastName());
            employeeEntity.setEmail(model.getEmail());
            employeeEntity.setStatus(model.getStatus());
            employeeEntity.setRole(roleRepository.findById(model.getRoleID()));
            result.setMessage("Update successfully");
            result.setData(employeeModel(employeeRepository.save(employeeEntity)));
        }

        return result;
    }

    public ServiceResult searchProductByName(ProductNameRequest request) {
        ServiceResult result = new ServiceResult();
        List<ProductByNameModel> productEntityList = productByNameModelList(request.getName());
        if (productEntityList.isEmpty()) {
            result.setMessage("No data");
            result.setStatus(ServiceResult.Status.FAILED);
        } else {
            result.setMessage("Search products successfully");
            result.setData(productEntityList);
        }
        return result;
    }

    public ServiceResult searchProductByCat(ProductByCatRequest request) {
        ServiceResult result = new ServiceResult();
        List<ProductByNameModel> productEntityList = productByCatModelList(request.getId());
        if (productEntityList.isEmpty()) {
            result.setMessage("No data");
            result.setStatus(ServiceResult.Status.FAILED);
        } else {
            result.setMessage("Search products successfully");
            result.setData(productEntityList);
        }
        return result;
    }

    public ServiceResult createOrder(OrderCreateRequest request) {
        ServiceResult result = new ServiceResult();
        OrderEntity createdOrder = createOrderEntity(request);
        if (createdOrder != null) orderRepository.save(createdOrder);
        orderDetailRepository.saveAll(createOrderDetailList(request.getOrderDetailCreateRequestList(), createdOrder));

        return result;
    }

    private OrderDetailEntity createOrderDetail(OrderDetailCreateRequest request, OrderEntity order) {
        ProductEntity product = productRepository.findById(request.getProductID()).orElse(null);
        if (product != null && order != null) {
            OrderDetailEntity orderDetail = new OrderDetailEntity(request.getPrice(),
                    request.getQuantity(),
                    product,
                    order);
            return orderDetail;
        } else return null;
    }

    private List<OrderDetailEntity> createOrderDetailList(List<OrderDetailCreateRequest> requestList, OrderEntity order) {
        List<OrderDetailEntity> orderDetailList = new ArrayList<>();
        for (OrderDetailCreateRequest detailCreateRequest : requestList) {
            orderDetailList.add(createOrderDetail(detailCreateRequest, order));
        }
        return orderDetailList;
    }

    private OrderEntity createOrderEntity(OrderCreateRequest request) {
        EmployeeEntity employee = employeeRepository.findById(request.getEmployeeID()).orElse(null);
        ShopEntity shop = shopRepository.findById(request.getShopID()).orElse(null);

        if (employee != null && shop != null) {
            OrderEntity order = new OrderEntity(request.getStatus(),
                    request.getTotalPrice(),
                    employee,
                    shop,
                    null);
            return order;
        } else return null;
    }

    public ServiceResult deleteOrder(OrderDeleteRequest request) {
        ServiceResult result = new ServiceResult();
        int index = isOrderDeleted(request.getId(), request.isDeleted());

        if (index == 0) {
            result.setMessage("Order not found");
            result.setStatus(ServiceResult.Status.FAILED);
        } else if (index == 1) {
            result.setMessage("Nothing changes");
            result.setStatus(ServiceResult.Status.FAILED);
        } else result.setMessage("Delete order successfully");
        return result;
    }

    private int isOrderDeleted(int id, boolean deleted) {
        OrderEntity deletedOrder = orderRepository.findById(id).orElse(null);

        if (deletedOrder == null) return 0;
        else if (deletedOrder.isDeleted() == deleted) return 1;
        else {
            deletedOrder.setDeleted(deleted);
            orderRepository.save(deletedOrder);
        }
        return 2;
    }

    public ServiceResult deleteProduct(ProductDeleteRequest request) {
        ServiceResult result = new ServiceResult();
        int index = isProductDeleted(request.getId(), request.isDeleted());

        if (index == 0) {
            result.setMessage("Product not found");
            result.setStatus(ServiceResult.Status.FAILED);
        } else if (index == 1) {
            result.setMessage("Nothing changes");
            result.setStatus(ServiceResult.Status.FAILED);
        } else result.setMessage("Delete product successfully");
        return result;
    }

    private int isProductDeleted(int id, boolean deleted) {
        ProductEntity deletedProduct = productRepository.findById(id).orElse(null);

        if (deletedProduct == null) return 0;
        else if (deletedProduct.isDeleted() == deleted) return 1;
        else {
            deletedProduct.setDeleted(deleted);
            productRepository.save(deletedProduct);
        }
        return 2;
    }
}