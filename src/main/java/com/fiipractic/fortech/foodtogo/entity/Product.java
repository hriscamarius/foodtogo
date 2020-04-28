
package com.fiipractic.fortech.foodtogo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private String name;
    private String category;
    private double price;
    private String ingredients;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "vendors_products", joinColumns = @JoinColumn(name = "product_code"), inverseJoinColumns = @JoinColumn(name = "vendors_user_id"))
    private Vendor vendor;
}
