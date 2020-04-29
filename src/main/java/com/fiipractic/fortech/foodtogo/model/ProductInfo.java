package com.fiipractic.fortech.foodtogo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {

    private Long id;
    private String name;
    private double price;
    private String restaurantName;


}
