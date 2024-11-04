package com.ecommerce.library.repository;


import com.ecommerce.library.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Modifying
    @Query(value = "UPDATE cart_items SET shopping_cart_id=null WHERE shopping_cart_id=?1", nativeQuery = true)
    void deleteCartItemBy(Long cartId);
}
