package com.ecommerce.library.repository;

import com.ecommerce.library.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.isActivated=true AND p.isDeleted=false")
    List<Product> getAllProduct();

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1% OR p.description LIKE %?1%")
    List<Product> findAllByNameOrDescription(String keyword);

    @Query("SELECT p FROM Product p JOIN Category c ON p.category.id=c.id " +
            "WHERE p.category.name=?1 AND p.isDeleted=false AND p.isActivated=true")
    List<Product> findAllByCategory(String category);

    @Query(value = "SELECT p.product_id, p.name, p.description, p.current_quantity, " +
            "p.cost_price, p.sale_price,p.image, p.category_id, p.is_deleted, p.is_activated FROM products p " +
            "WHERE p.is_deleted=false AND p.is_activated=true ORDER BY RANDOM() DESC LIMIT 9", nativeQuery = true)
    List<Product> randomProduct();

    @Query(value = "SELECT p.product_id, p.name, p.description, p.current_quantity, " +
            "p.cost_price, p.sale_price,p.image, p.category_id, p.is_deleted, p.is_activated FROM products p " +
            "WHERE p.is_deleted=false AND p.is_activated=true ORDER BY p.cost_price ASC LIMIT 9", nativeQuery = true)
    List<Product> filterLowerProducts();

    @Query(value = "SELECT p.product_id, p.name, p.description, p.current_quantity, " +
            "p.cost_price, p.sale_price,p.image, p.category_id, p.is_deleted, p.is_activated FROM products p " +
            "WHERE p.is_deleted=false AND p.is_activated=true ORDER BY p.cost_price DESC LIMIT 9", nativeQuery = true)
    List<Product> filterHighProducts();


    @Query(value = "SELECT p.product_id, p.name, p.description, p.current_quantity, p.cost_price, p.category_id," +
            "p.sale_price, p.image, p.is_activated, p.is_deleted FROM products p WHERE p.is_deleted=false AND p.is_activated=true LIMIT 4", nativeQuery = true)
    List<Product> listViewProduct();

    @Query("SELECT p FROM Product p WHERE p.category.id=?1 AND p.isActivated=true AND p.isDeleted=false")
    List<Product> getProductByCategoryId(Long id);

    @Query("SELECT p FROM Product p WHERE p.name like %?1% OR p.description LIKE %?1%")
    List<Product> searchProducts(String keyword);


}
