package com.ecommerce.library.repository;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Modifying
    @Query(value = "UPDATE Category SET name =?1 WHERE id=?2")
    Category update(String name, Long id);

    @Query("SELECT new com.ecommerce.library.dto.CategoryDto (c.id, c.name, count(p.category.id))" +
            "from Category c LEFT JOIN Product p ON c.id=p.category.id WHERE c.isActivated=true and c.isDeleted=false GROUP BY c.id")
    List<CategoryDto> getCategoriesAndSize();

    @Query("SELECT c FROM Category c WHERE c.isActivated=true")
    List<Category> findAllByActivatedTrue();

}
