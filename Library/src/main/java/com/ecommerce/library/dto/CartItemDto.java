package com.ecommerce.library.dto;

import com.ecommerce.library.model.ShoppingCart;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemDto {
    Long id;
    ShoppingCartDto cart;
    ProductDto product;
    int quantity;
    double unitPrice;
}
