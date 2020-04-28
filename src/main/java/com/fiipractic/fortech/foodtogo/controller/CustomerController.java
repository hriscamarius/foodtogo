package com.fiipractic.fortech.foodtogo.controller;

import com.fiipractic.fortech.foodtogo.entity.Customer;
import com.fiipractic.fortech.foodtogo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/customer")
@Transactional
public class CustomerController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String customerProfile(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken)){
            Customer customer = (Customer) userService.findByUsername(auth.getName());
            model.addAttribute("customer", customer);
        }
        return "customer/profile";
    }
}
