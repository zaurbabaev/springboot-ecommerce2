package com.ecommerce.library.repository;

import com.ecommerce.library.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrederRepository extends JpaRepository<Order, Long> {

}
