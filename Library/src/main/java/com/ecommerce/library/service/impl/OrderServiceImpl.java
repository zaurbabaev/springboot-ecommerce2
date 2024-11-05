package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.ShoppingCartDto;
import com.ecommerce.library.model.Order;
import com.ecommerce.library.repository.CustomerRepository;
import com.ecommerce.library.repository.OrderDetailRepository;
import com.ecommerce.library.repository.OrderRepository;
import com.ecommerce.library.service.OrderService;
import com.ecommerce.library.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CustomerRepository customerRepository;
    private final ShoppingCartService shoppingCartService;

    @Override
    public Order save(ShoppingCartDto shoppingCartDto) {
        return null;
    }

    @Override
    public List<Order> findAll(String username) {
        return List.of();
    }

    @Override
    public List<Order> findAllOrders() {
        return List.of();
    }

    @Override
    public Order acceptOrder(Long id) {
        return null;
    }

    @Override
    public void cancelOrder(Long id) {

    }
}
