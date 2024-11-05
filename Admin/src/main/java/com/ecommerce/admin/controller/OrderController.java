package com.ecommerce.admin.controller;

import com.ecommerce.library.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

}
