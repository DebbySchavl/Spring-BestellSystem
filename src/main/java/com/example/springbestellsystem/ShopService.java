package com.example.springbestellsystem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;


    public Product getProduct(String id) {
        return productRepo.get(id);
    }

    public List<Product> listAllProducts() {
        return productRepo.listProductList();
    }

    public void deleteProduct(String id) {
        this.productRepo.delete(id);
    }

    public Order getOrder(String id) {
        return orderRepo.getOrder(id);
    }

    public Order addOrder(List<String> productIds) {
        List<Product> addedProducts = new ArrayList<>();

        for (String productId : productIds) {
            Product product = productRepo.get(productId);
            if (product == null) {

                return null;
            }
            addedProducts.add(product);
        }

        Order newOrder = new Order("33", addedProducts);
        return orderRepo.addOrder(newOrder);

    }

    public List<Order> listAllOrders() {
        return orderRepo.listAllOrders();
    }

    public void deleteOrder(String id) {
        this.orderRepo.delete(id);
    }

    public Order updateOrder(Order order) {
        return orderRepo.addOrder(order);

    }
}
