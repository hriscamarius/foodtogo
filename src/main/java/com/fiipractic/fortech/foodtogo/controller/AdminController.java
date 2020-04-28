package com.fiipractic.fortech.foodtogo.controller;

import com.fiipractic.fortech.foodtogo.dto.ProductDto;
import com.fiipractic.fortech.foodtogo.entity.Product;
import com.fiipractic.fortech.foodtogo.entity.User;
import com.fiipractic.fortech.foodtogo.service.ProductServiceImpl;
import com.fiipractic.fortech.foodtogo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String allUsers(Model model){
        List<User> users = userService.allUsers();
        model.addAttribute("userList", users);
        return "admin/userList";
    }

    @GetMapping("/profile")
    public String adminProfile(Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken)){
            User user = userService.findByUsername(auth.getName());
            model.addAttribute("user", user);
        }
        return "admin/profile";
    }

    @RequestMapping("/products")
    public String productList(Model model){
        List<Product> result = productService.findAllProducts();
        model.addAttribute("productList", result);
        return "/admin/allProducts";
    }
}
