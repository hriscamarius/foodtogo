package com.fiipractic.fortech.foodtogo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vendors")
public class Vendor extends User{

    private String restaurantName;
    private String specificRestaurant;
    private String phoneNo;
    private String address;
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "vendors_products", joinColumns = @JoinColumn(name = "vendors_user_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;
    @OneToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "vendors_orders", joinColumns = @JoinColumn(name = "vendors_user_id"), inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<Order> vendorOrders;
}