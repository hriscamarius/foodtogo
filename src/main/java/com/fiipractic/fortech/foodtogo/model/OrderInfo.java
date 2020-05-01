package com.fiipractic.fortech.foodtogo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfo {

    private Long id;
    private Date orderDate;
    private int orderNum;
    private String status;
    private double amount;
    private String customerName;
    private String customerAddress;
    private String customerEmail;
    private String customerPhone;
    private String restaurantName;
    private List<OrderDetailInfo> details;
}
