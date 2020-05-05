package com.fiipractic.fortech.foodtogo.controller;

import com.fiipractic.fortech.foodtogo.entity.*;
import com.fiipractic.fortech.foodtogo.model.*;
import com.fiipractic.fortech.foodtogo.service.OrderServiceImpl;
import com.fiipractic.fortech.foodtogo.service.ProductServiceImpl;
import com.fiipractic.fortech.foodtogo.service.UserService;
import org.modelmapper.Converters;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private UserService userService;
    @Autowired
    private OrderServiceImpl orderService;
    private ModelMapper modelMapper = new ModelMapper();

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
        return "admin/allProducts";
    }

    @GetMapping("/deleteProduct/{productId}")
    public String deleteProduct(@PathVariable Long productId){
        productService.deleteById(productId);
        return "redirect:/admin/products";
    }

    @GetMapping("/editProduct/{productId}")
    public String showEditProductForm(@PathVariable Long productId, Model model) {

        ProductRegistrationDto productRegistrationDto = new ProductRegistrationDto();
        Product product = productService.findById(productId);
        if(product != null){
            productRegistrationDto.setName(product.getName());
            productRegistrationDto.setCategory(product.getCategory());
            productRegistrationDto.setPrice(product.getPrice());
            productRegistrationDto.setIngredients(product.getIngredients());
        }else{
            return "/products";
        }
        model.addAttribute("productRegistrationDto", productRegistrationDto);
        model.addAttribute("productId", productId);
        return "/admin/editProduct";
    }

    @PostMapping("/editProduct/{productId}")
    public String updateProduct(@PathVariable Long productId,  @ModelAttribute("productRegistrationDto") @Valid ProductRegistrationDto prd,
                                BindingResult result){
        if(result.hasErrors()){
            return "/admin/editProduct";
        }
        productService.updateProduct(productId, prd);
        return "redirect:/admin/products";
    }

    @GetMapping("/deleteUser/{userId}")
    public String deleteUser(@PathVariable Long userId){
            User user = userService.findById(userId);
            Collection<Role> roles = user.getRoles();
            for (Role role: roles) {
                if(!role.getName().equals(Role.ADMIN)){
                    userService.deleteById(userId);
                }
            }
        return "redirect:/admin/users";
    }

    @GetMapping("/editUser/{userId}")
    public String showEditUserForm(@PathVariable Long userId, Model model){
        User user = userService.findById(userId);
        if(user != null){
            if(user instanceof Customer){
                Customer customer = (Customer) user;
                CustomerUpdateForm customerUpdateForm = modelMapper.map(customer, CustomerUpdateForm.class);
                model.addAttribute("customerUpdateForm", customerUpdateForm);
                model.addAttribute("username", customer.getUsername());
                return "/admin/profileCustomer";
            }
            if(user instanceof Vendor){
                Vendor vendor = (Vendor)user;
                modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
                VendorUpdateForm vendorUpdateForm = modelMapper.map(vendor, VendorUpdateForm.class);
                model.addAttribute("vendorUpdateForm", vendorUpdateForm);
                model.addAttribute("username", vendor.getUsername());
                return "admin/profileVendor";
            }
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/editCustomer/{userId}")
    public String updateCustomerProfile(@ModelAttribute("customerUpdateForm") @Valid CustomerUpdateForm customerUpdateForm,
                                        @PathVariable Long userId, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/profileCustomer";
        }
        User user = userService.findById(userId);
        if(user != null){
            userService.updateCustomer(user.getId(), customerUpdateForm);
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/editVendor/{userId}")
    public String updateVendorProfile(@ModelAttribute("vendorUpdateForm") @Valid VendorUpdateForm vendorUpdateForm,
                                      @PathVariable Long userId, BindingResult result){
        if(result.hasErrors()){
            return "admin/profileVendor";
        }
        User user = userService.findById(userId);
        if(user != null){
            userService.updateVendor(user.getId(), vendorUpdateForm);
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/orders")
    public String allOrders(Model model){
        List<OrderInfo> orderInfoList = orderService.findAll();
        model.addAttribute("orderInfoList", orderInfoList);
        return "orderList";
    }

    @GetMapping("/orderInfo")
    public String orderView(Model model, @RequestParam("orderId") Long orderId) {
        OrderInfo orderInfo = null;
        if (orderId != null) {
            orderInfo = orderService.findOrderInfoById(orderId);
        }
        if (orderInfo == null) {
            return "redirect:/admin/orders";
        }
        List<OrderDetailInfo> details = orderService.listOrderDetailInfos(orderId);
        orderInfo.setDetails(details);
        model.addAttribute("orderInfo", orderInfo);
        return "order";
    }
}
