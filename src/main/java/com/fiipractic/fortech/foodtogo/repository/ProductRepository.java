package com.fiipractic.fortech.foodtogo.repository;

import com.fiipractic.fortech.foodtogo.entity.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    Product findByIdAndVendorId(Long productId, Long vendorId);
    Product findByNameAndVendorId(String name, Long vendorId);
    boolean existsByNameAndVendorUsername(String name, String username);
    boolean existsByIdAndVendorUsername(Long productId, String username);
    boolean existsByNameAndVendorId(String name, Long vendorId);
    void deleteByIdAndVendorUsername(Long productId, String username);

    @Modifying
    @Transactional
    @Query("update Product p set p.name=:name, p.category=:category, p.price=:price, p.ingredients=:ingredients where p.id=:id")
    void updateProduct(@Param("id") Long productId, @Param("name") String name, @Param("category") String category, @Param("price") double price, @Param("ingredients") String ingredients);
}
