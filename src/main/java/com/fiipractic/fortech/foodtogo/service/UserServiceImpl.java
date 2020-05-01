package com.fiipractic.fortech.foodtogo.service;

import com.fiipractic.fortech.foodtogo.dto.CustomerDto;
import com.fiipractic.fortech.foodtogo.dto.VendorDto;
import com.fiipractic.fortech.foodtogo.entity.Customer;
import com.fiipractic.fortech.foodtogo.entity.Role;
import com.fiipractic.fortech.foodtogo.entity.User;
import com.fiipractic.fortech.foodtogo.entity.Vendor;
import com.fiipractic.fortech.foodtogo.repository.RoleRepository;
import com.fiipractic.fortech.foodtogo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private ModelMapper modelMapper = new ModelMapper();

    public List<User> allUsers(){
        return (List<User>) userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user!=null){
            boolean enabled = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,mapRolesToAuthorities(user.getRoles()));
        }
        throw new UsernameNotFoundException("User " + username + " not found");
    }
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void saveVendor(VendorDto vendorDto) {
        Vendor vendor = new Vendor();
        vendor.setUsername(vendorDto.getUsername());
        vendor.setEmail(vendorDto.getEmail());
        vendor.setPassword(passwordEncoder.encode(vendorDto.getPassword()));
        vendor.setRestaurantName(vendorDto.getRestaurantName());
        vendor.setSpecificRestaurant(vendorDto.getSpecificRestaurant());
        vendor.setPhoneNo(vendorDto.getPhoneNo());
        vendor.setAddress(vendorDto.getAddress());
        vendor.setRoles(Collections.singletonList(roleRepository.findByName(Role.VENDOR)));
        userRepository.save(vendor);
    }

    @Override
    public void saveCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setUsername(customerDto.getUsername());
        customer.setEmail(customerDto.getEmail());
        customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
        customer.setName(customerDto.getName());
        customer.setDateOfBirth(customerDto.getDateOfBirth());
        customer.setPhoneNo(customerDto.getPhoneNo());
        customer.setAddress(customerDto.getAddress());
        customer.setRoles(Collections.singletonList(roleRepository.findByName(Role.CUSTOMER)));
        userRepository.save(customer);
    }

    @Override
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }
}
