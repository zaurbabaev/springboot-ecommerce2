package com.ecommerce.library.service;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.dto.ShoppingCart;

public interface ShoppingCartService {

    com.ecommerce.library.model.ShoppingCart addItemToCart(ProductDto productDto, int quantity, String username);

    com.ecommerce.library.model.ShoppingCart updateCart(ProductDto productDto, int quantity, String username);

    com.ecommerce.library.model.ShoppingCart removeItemFromCart(ProductDto productDto, String username);

    ShoppingCart addItemToCartSession(ShoppingCart cartDto, ProductDto productDto, int quantity);

    ShoppingCart updateCartSession(ShoppingCart cartDto, ProductDto productDto, int quantity);

    ShoppingCart removeItemFromCartSession(ShoppingCart cartDto, ProductDto productDto, int quantity);

    com.ecommerce.library.model.ShoppingCart combineCart(ShoppingCart cartDto, com.ecommerce.library.model.ShoppingCart cart);

    void deleteCartById(Long id);

    com.ecommerce.library.model.ShoppingCart getCart(String username);
}
