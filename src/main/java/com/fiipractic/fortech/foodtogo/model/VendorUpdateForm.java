package com.fiipractic.fortech.foodtogo.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class VendorUpdateForm {

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
    @Pattern(regexp = "(\\+\\d{1})?\\d{10}", message = "Invalid phone no")
    private String phoneNo;
    @NotEmpty(message = "Please provide an Address")
    private String address;
}
