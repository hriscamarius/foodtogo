package com.fiipractic.fortech.foodtogo.controller;

import com.fiipractic.fortech.foodtogo.dto.CustomerDto;
import com.fiipractic.fortech.foodtogo.dto.ProductDto;
import com.fiipractic.fortech.foodtogo.dto.ProductRegistrationDto;
import com.fiipractic.fortech.foodtogo.dto.VendorDto;
import com.fiipractic.fortech.foodtogo.entity.User;
import com.fiipractic.fortech.foodtogo.service.ProductServiceImpl;
import com.fiipractic.fortech.foodtogo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MainController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }

    @RequestMapping("/403")
    public String accessDenied() {
        return "/403";
    }

    @RequestMapping("/productList")
    public String productList(Model model){
        List<ProductDto> result = productService.findAllProductDtos();
        model.addAttribute("productList", result);
        return "productList";
    }

    @ModelAttribute("vendor")
    public VendorDto vendorRegistrationDto() {
        return new VendorDto();
    }

    @GetMapping("/registerVendor")
    public String showRegisterVendorForm(Model model){
        return "registerVendor";
    }

    @PostMapping("/registerVendor")
    public String registerVendor(@ModelAttribute("vendor") @Valid VendorDto vendorDto,
                                 BindingResult result){
        User existing = userService.findByUsername(vendorDto.getUsername());
        if(existing != null) {
            result.rejectValue("username", null, "There is already an account registered with that username");
        }
        if(result.hasErrors()){
            return "registerVendor";
        }
        userService.saveVendor(vendorDto);
        return "redirect:/registerVendor?success";
    }

    @ModelAttribute("customer")
    public CustomerDto customerRegistrationDto() {
        return new CustomerDto();
    }

    @GetMapping("/registerCustomer")
    public String showRegisterCustomerForm(Model model){
        return "registerCustomer";
    }

    @PostMapping("/registerCustomer")
    public String registerCustomer(@ModelAttribute("customer") @Valid CustomerDto customerDto,
                                 BindingResult result){
        User existing = userService.findByUsername(customerDto.getUsername());
        if(existing != null) {
            result.rejectValue("username", null, "There is already an account registered with that username");
        }
        if(result.hasErrors()){
            return "registerCustomer";
        }
        userService.saveCustomer(customerDto);
        return "redirect:/registerCustomer?success";
    }
}
