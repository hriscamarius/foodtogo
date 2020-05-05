package com.fiipractic.fortech.foodtogo.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class CustomerUpdateForm {

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
    @Pattern(regexp = "(\\+\\d{1})?\\d{10}", message = "Invalid phone no")
    @NotEmpty(message = "Address cannot be empty")
    private String address;
}
