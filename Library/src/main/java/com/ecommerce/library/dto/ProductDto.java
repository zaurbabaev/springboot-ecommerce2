package com.ecommerce.library.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDto {
    Long id;
    String name;
    String description;
    int currentQuantity;
    double costPrice;
    double salePrice;
    String image;
    CategoryDto category;
    boolean isActivated;
    boolean isDeleted;
    String currentPage;
}
