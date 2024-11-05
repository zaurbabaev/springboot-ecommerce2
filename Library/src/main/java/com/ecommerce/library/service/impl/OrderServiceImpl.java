package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.ShoppingCart;
import com.ecommerce.library.enums.OrderStatus;
import com.ecommerce.library.enums.PaymentMethod;
import com.ecommerce.library.model.CartItem;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Order;
import com.ecommerce.library.model.OrderDetail;
import com.ecommerce.library.repository.CustomerRepository;
import com.ecommerce.library.repository.OrderDetailRepository;
import com.ecommerce.library.repository.OrderRepository;
import com.ecommerce.library.service.OrderService;
import com.ecommerce.library.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CustomerRepository customerRepository;
    private final ShoppingCartService shoppingCartService;

    @Override
    @Transactional
    public Order save(ShoppingCart shoppingCart) {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setCustomer(shoppingCart.getCustomer());
        order.setTax(2);
        order.setTotalPrice(shoppingCart.getTotalPrice());
        order.setAccept(false);
        order.setPaymentMethod(PaymentMethod.CASH);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setQuantity(shoppingCart.getTotalItems());

        List<OrderDetail> orderDetails = new ArrayList<>();

        for (CartItem cartItem : shoppingCart.getCartItems()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(cartItem.getProduct());
            orderDetailRepository.save(orderDetail);
        }
        order.setOrderDetailsList(orderDetails);
        shoppingCartService.deleteCartById(shoppingCart.getId());
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAll(String username) {
        Customer customer = customerRepository.findByUsername(username);
        return customer.getOrders();
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order acceptOrder(Long id) {
        Order order = orderRepository.getReferenceById(id);
        order.setAccept(true);
        order.setDeliveryDate(new Date());
        return orderRepository.save(order);
    }

    @Override
    public void cancelOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
