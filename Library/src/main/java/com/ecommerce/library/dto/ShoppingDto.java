package com.ecommerce.library.dto;

import com.ecommerce.library.model.Customer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoppingDto {
    Long id;
    Customer customer;
    double totalPrice;
    int totalItems;
    Set<CartItemDto> cartItems;
}
