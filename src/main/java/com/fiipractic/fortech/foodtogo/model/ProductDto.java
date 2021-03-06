package com.fiipractic.fortech.foodtogo.model;

import lombok.Data;

@Data
public class ProductDto {

    private Long id;
    private String name;
    private String category;
    private double price;
    private String ingredients;
    private String restaurantName;
}
