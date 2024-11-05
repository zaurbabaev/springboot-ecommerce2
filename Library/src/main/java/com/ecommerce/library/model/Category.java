package com.ecommerce.library.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "categories", uniqueConstraints = @UniqueConstraint(columnNames = "name"))

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    Long id;
    String name;
    boolean isActivated;
    boolean isDeleted;

    public Category() {
        this.isActivated = true;
        this.isDeleted = false;
    }
}
