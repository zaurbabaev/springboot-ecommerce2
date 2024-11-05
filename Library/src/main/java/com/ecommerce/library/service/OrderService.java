package com.ecommerce.library.service;

import com.ecommerce.library.dto.ShoppingCartDto;
import com.ecommerce.library.model.Order;

import java.util.List;

public interface OrderService {

    Order save(ShoppingCartDto shoppingCartDto);

    List<Order> findAll(String username);

    List<Order> findAllOrders();

    Order acceptOrder(Long id);

    void cancelOrder(Long id);


}
