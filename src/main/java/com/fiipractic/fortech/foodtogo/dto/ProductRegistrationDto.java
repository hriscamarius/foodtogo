package com.fiipractic.fortech.foodtogo.dto;


import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ProductRegistrationDto {

    @NotEmpty(message = "Please provide a Product Name")
    private String name;
    @NotEmpty(message = "Please provide a Category")
    private String category;
    @NotNull(message = "Price cannot be null")
    @NumberFormat
    private double price;
    @NotEmpty(message = "Please provide some ingredients")
    private String ingredients;
}
