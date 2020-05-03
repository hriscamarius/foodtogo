package com.fiipractic.fortech.foodtogo.service;

import com.fiipractic.fortech.foodtogo.model.CustomerDto;
import com.fiipractic.fortech.foodtogo.model.CustomerUpdateForm;
import com.fiipractic.fortech.foodtogo.model.VendorDto;
import com.fiipractic.fortech.foodtogo.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    List<User> allUsers();
    User findByUsername(String username);
    void saveVendor(VendorDto vendorDto);
    void saveCustomer(CustomerDto customerDto);
    void deleteById(Long userId);
    void updateCustomer(Long id, CustomerUpdateForm customerUpdateForm);
    User findById(Long userId);
}