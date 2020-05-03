package com.fiipractic.fortech.foodtogo.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class VendorUpdateForm {

    @Length(min = 5, message = "*Your user name must have at least 5 characters")
    @NotEmpty(message = "Please provide a username")
    private String username;
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "Please provide an Email")
    private String email;
    @NotEmpty(message = "Please provide your password")
    private String password;

    @NotEmpty(message = "Please provide a Restaurant Name")
    private String restaurantName;
    @NotEmpty(message = "Please provide a Specific Restaurant")
    private String specificRestaurant;
    @NotEmpty(message = "Please provide a Phone No")
    private String phoneNo;
    @NotEmpty(message = "Please provide an Address")
    private String address;
}
