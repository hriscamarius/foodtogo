package com.fiipractic.fortech.foodtogo.repository;

import com.fiipractic.fortech.foodtogo.entity.OrderDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends CrudRepository<OrderDetail, Long> {
    List<OrderDetail> findAllByOrderId(Long orderId);
}
