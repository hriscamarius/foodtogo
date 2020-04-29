package com.fiipractic.fortech.foodtogo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailInfo {

    private Long id;
    private Long productId;
    private String productName;

    private int quanity;
    private double price;
    private double amount;


}
