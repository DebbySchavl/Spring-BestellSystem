package com.example.springbestellsystem;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepo {

    private final Map<String, Order> orderList;

    public OrderRepo() {
        orderList = new HashMap<>();

      /*  orderList.put("1", new Order(
                "1", List.of(new Product(
                "1", "Banana")
        )));

        orderList.put("2", new Order(
                "2", List.of(new Product(
                "2", "Pineapple")
        )));*/
    }

    public List<Order> listAllOrders() {
        return new ArrayList<>(orderList.values());
    }

    public Order getOrder(String id) {
        return orderList.get(id);
    }

    public Order addOrder(Order orderToAdd) {
        orderList.put(orderToAdd.getId(), orderToAdd);
        return orderToAdd;
    }

    public void delete(String id) {
        this.orderList.remove(id);
    }
}
