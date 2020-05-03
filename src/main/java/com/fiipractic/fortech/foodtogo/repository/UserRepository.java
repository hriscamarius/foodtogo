package com.fiipractic.fortech.foodtogo.repository;

import com.fiipractic.fortech.foodtogo.entity.User;
import com.fiipractic.fortech.foodtogo.entity.Vendor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Long>{
    User findByUsername(String s);
    @Modifying
    @Transactional
    @Query("update Customer c set c.username=:username, c.email=:email, c.password=:password, c.name=:name, c.dateOfBirth=:dateOfBirth, c.phoneNo=:phoneNo, c.address=:address where c.id=:id")
    void updateCustomer(@Param("id") Long id, @Param("username") String username, @Param("email") String email, @Param("password") String password,
                        @Param("name") String name, @Param("dateOfBirth")Date dateOfBirth, @Param("phoneNo") String phoneNo, @Param("address") String address);
    @Modifying
    @Transactional
    @Query("update Vendor v set v.username=:username, v.email=:email, v.password=:password, v.restaurantName=:restaurantName, v.specificRestaurant=:specificRestaurant, v.phoneNo=:phoneNo, v.address=:address where v.id=:id")
    void updateVendor(@Param("id") Long id, @Param("username") String username, @Param("email") String email, @Param("password") String password,
                        @Param("restaurantName") String restaurantName, @Param("specificRestaurant")String specificRestaurant, @Param("phoneNo") String phoneNo, @Param("address") String address);

}
