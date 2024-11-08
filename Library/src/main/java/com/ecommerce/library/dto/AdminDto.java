package com.ecommerce.library.dto;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {
    @Size(min = 3, max = 10, message = "Invalid first name!(3-10 characters)")
    String firstname;
    @Size(min = 3, max = 10, message = "Invalid last name!(3-10 characters)")
    String lastname;
    String username;
    @Size(min = 5, max = 15, message = "Invalid password !(5-15 characters)")
    String password;
    String repeatPassword;
}
