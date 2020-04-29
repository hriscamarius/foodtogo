package com.fiipractic.fortech.foodtogo.repository;

import com.fiipractic.fortech.foodtogo.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    @Query("SELECT max(orderNum) FROM Order")
    Integer maxOrderNum();
}
