package com.fiipractic.fortech.foodtogo.controller;

import com.fiipractic.fortech.foodtogo.dto.ProductRegistrationDto;
import com.fiipractic.fortech.foodtogo.entity.Product;
import com.fiipractic.fortech.foodtogo.entity.User;
import com.fiipractic.fortech.foodtogo.model.OrderDetailInfo;
import com.fiipractic.fortech.foodtogo.model.OrderInfo;
import com.fiipractic.fortech.foodtogo.service.OrderServiceImpl;
import com.fiipractic.fortech.foodtogo.service.ProductServiceImpl;
import com.fiipractic.fortech.foodtogo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        userService.deleteById(userId);
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
