package com.fiipractic.fortech.foodtogo.service;

import com.fiipractic.fortech.foodtogo.dto.CustomerDto;
import com.fiipractic.fortech.foodtogo.dto.VendorDto;
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
}