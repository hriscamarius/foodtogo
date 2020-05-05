package com.fiipractic.fortech.foodtogo.model;

import com.fiipractic.fortech.foodtogo.constraint.FieldMatch;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@FieldMatch.List({@FieldMatch(first="password", second = "confirmPassword", message = "The password fields must match"),
        @FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")})
@Data
public class VendorDto {

    @Length(min = 5, message = "*Your user name must have at least 5 characters")
    @NotEmpty(message = "Please provide a username")
    @Pattern(regexp = "[A-Za-z0-9_]+")
    private String username;

    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "Please provide an Email")
    private String email;

    @Email
    @NotEmpty
    private String confirmEmail;

    @NotEmpty(message = "Please provide your password")
    private String password;

    @NotEmpty(message = "Please confirm your password")
    private String confirmPassword;

    @NotEmpty(message = "Please provide a Restaurant Name")
    private String restaurantName;

    @NotEmpty(message = "Please provide a Specific Restaurant")
    private String specificRestaurant;

    @NotEmpty(message = "Please provide a Phone No")
    private String phoneNo;

    @NotEmpty(message = "Please provide an Address")
    private String address;

    @AssertTrue
    private Boolean terms;
}
