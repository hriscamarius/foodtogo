package com.fiipractic.fortech.foodtogo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class CustomerForm {

    @Length(min = 5, message = "*Your user name must have at least 5 characters")
    @NotEmpty(message = "Please provide a username")
    private String name;
    @NotEmpty(message = "Please provide an Address")
    private String address;
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "Please provide an Email")
    private String email;
    @NotEmpty(message = "Please provide a Phone No")
    @Pattern(regexp = "(\\+\\d{1})?\\d{10}", message = "Invalid phone no")
    private String phoneNo;
    private boolean valid;

    public CustomerForm(CustomerInfo customerInfo) {
        if (customerInfo != null) {
            this.name = customerInfo.getName();
            this.address = customerInfo.getAddress();
            this.email = customerInfo.getEmail();
            this.phoneNo = customerInfo.getPhoneNo();
        }
    }
}
