package com.fiipractic.fortech.foodtogo.model;

import com.fiipractic.fortech.foodtogo.constraint.FieldMatch;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

@FieldMatch.List({@FieldMatch(first="password", second = "confirmPassword", message = "The password fields must match"),
        @FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")})
@Data
public class CustomerDto {

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


    @NotEmpty(message = "Please provide a Name")
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    @NotEmpty(message = "Please provide a Phone No")
    private String phoneNo;
    @NotEmpty(message = "Please provide an Address")
    private String address;
    @AssertTrue
    private Boolean terms;
}
