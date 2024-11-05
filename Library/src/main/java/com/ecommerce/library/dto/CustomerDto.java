package com.ecommerce.library.dto;

import com.ecommerce.library.model.City;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerDto {
    String name;
    String lastname;
    String username;
    String password;
    String confirmPassword;
    String phoneNumber;
    String address;
    City city;
    String image;
    String country;

}
