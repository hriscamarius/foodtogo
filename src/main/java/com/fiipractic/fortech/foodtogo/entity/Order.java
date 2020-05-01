package com.fiipractic.fortech.foodtogo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order implements Serializable {

    private static final long serialVersionUID = -2576670215015463100L;

    public final static String PENDING = "PENDING";
    public final static String IN_PROGRESS = "IN_PROGRESS";
    public final static String DELIVERED = "DELIVERED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    @NotNull
    private Date orderDate;
    @NotNull
    private int orderNum;
    @NotNull
    private String status;
    @NotNull
    private double amount;
    @NotNull
    private String customerName;
    @NotNull
    private String customerAddress;
    @NotNull
    private String customerEmail;
    @NotNull
    private String customerPhone;

    @ManyToOne(fetch =FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "vendors_orders", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "vendors_user_id"))
    private Vendor vendor;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinTable(name = "customers_orders", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "customers_user_id"))
    private Customer customer;
}
