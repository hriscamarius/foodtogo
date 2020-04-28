package com.fiipractic.fortech.foodtogo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
public class Customer extends User{

    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    private String address;
    private String phoneNo;
    @OneToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "customers_orders", joinColumns = @JoinColumn(name = "customers_user_id"), inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<Order> customerOrders;
}
