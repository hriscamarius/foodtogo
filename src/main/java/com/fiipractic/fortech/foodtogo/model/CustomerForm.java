package com.fiipractic.fortech.foodtogo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerForm {

    private String name;
    private String address;
    private String email;
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
