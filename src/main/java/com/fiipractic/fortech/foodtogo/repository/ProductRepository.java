package com.fiipractic.fortech.foodtogo.repository;

import com.fiipractic.fortech.foodtogo.entity.Product;
import com.fiipractic.fortech.foodtogo.entity.Vendor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    Product findByIdAndVendorId(Long productId, Long vendorId);
    Product findByNameAndVendorId(String name, Long vendorId);
    boolean existsByNameAndVendorUsername(String name, String username);
    boolean existsByIdAndVendorUsername(Long productId, String username);
    boolean existsByNameAndVendorId(String name, Long vendorId);
    void deleteByIdAndVendorUsername(Long productId, String username);
}
