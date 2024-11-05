package com.ecommerce.library.service;

import com.ecommerce.library.dto.ShoppingCart;
import com.ecommerce.library.model.Order;

import java.util.List;

public interface OrderService {

    Order save(ShoppingCart shoppingCart);

    List<Order> findAll(String username);

    List<Order> findAllOrders();

    Order acceptOrder(Long id);

    void cancelOrder(Long id);


}
