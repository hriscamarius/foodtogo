package com.fiipractic.fortech.foodtogo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    public final static String ADMIN = "ROLE_ADMIN";
    public final static String VENDOR = "ROLE_VENDOR";
    public final static String CUSTOMER = "ROLE_CUSTOMER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;
    @Column(name = "role")
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
}
