package com.fiipractic.fortech.foodtogo.controller;

import com.fiipractic.fortech.foodtogo.dto.ProductRegistrationDto;
import com.fiipractic.fortech.foodtogo.entity.Product;
import com.fiipractic.fortech.foodtogo.entity.Vendor;
import com.fiipractic.fortech.foodtogo.service.ProductServiceImpl;
import com.fiipractic.fortech.foodtogo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/profile")
    public String vendorProfile(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken)){
            Vendor vendor = (Vendor) userService.findByUsername(auth.getName());
            model.addAttribute("vendor", vendor);
        }
        return "vendor/profile";
    }

    @ModelAttribute("productRegistrationDto")
    public ProductRegistrationDto productRegistrationDto() {
        return new ProductRegistrationDto();
    }

    @GetMapping("/registerProduct")
    public String showProductForm(Model model){
        return "/vendor/registerProduct";
    }

    @PostMapping("/registerProduct")
    public String addProduct(@ModelAttribute("productRegistrationDto") @Valid ProductRegistrationDto productRegistrationDto, BindingResult result){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (productService.existsByNameAndVendorUsername(productRegistrationDto.getName(),auth.getName())) {
            result.rejectValue("name", null, "You already have an product registered with that name");
        }
        if (result.hasErrors()) {
            return "/vendor/registerProduct";
        }
        productService.save(productRegistrationDto);
        return "redirect:/vendor/registerProduct?success";
    }

    @GetMapping("/myproducts")
    public String vendorProducts(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Vendor vendor = (Vendor) userService.findByUsername(auth.getName());
        List<Product> result = vendor.getProducts();
        model.addAttribute("productList", result);
        return "/vendor/vendorProducts";
    }
}
