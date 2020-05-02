package com.fiipractic.fortech.foodtogo.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class CustomerUpdateForm {

    @Length(min = 5, message = "*Your user name must have at least 5 characters")
    @NotEmpty(message = "Please provide a username")
    private String username;
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    @NotEmpty(message = "Phone No cannot be empty")
    private String phoneNo;
    @NotEmpty(message = "Address cannot be empty")
    private String address;
}
