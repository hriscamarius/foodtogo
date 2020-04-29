package com.fiipractic.fortech.foodtogo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerInfo {

    private String name;
    private String address;
    private String email;
    private String phoneNo;

    private boolean valid;

}
