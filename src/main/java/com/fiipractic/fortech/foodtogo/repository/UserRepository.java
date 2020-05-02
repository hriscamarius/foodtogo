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

}
