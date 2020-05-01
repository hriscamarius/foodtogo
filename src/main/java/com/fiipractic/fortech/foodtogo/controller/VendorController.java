package com.fiipractic.fortech.foodtogo.controller;

import com.fiipractic.fortech.foodtogo.dto.ProductRegistrationDto;
import com.fiipractic.fortech.foodtogo.entity.Order;
import com.fiipractic.fortech.foodtogo.entity.Product;
import com.fiipractic.fortech.foodtogo.entity.Vendor;
import com.fiipractic.fortech.foodtogo.model.OrderDetailInfo;
import com.fiipractic.fortech.foodtogo.model.OrderInfo;
import com.fiipractic.fortech.foodtogo.service.OrderServiceImpl;
import com.fiipractic.fortech.foodtogo.service.ProductServiceImpl;
import com.fiipractic.fortech.foodtogo.service.UserServiceImpl;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private OrderServiceImpl orderService;
    private ModelMapper modelMapper = new ModelMapper();

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

    @GetMapping("/deleteProduct/{productId}")
    public String deleteProduct(@PathVariable Long productId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(productService.existsByIdAndVendorUsername(productId, auth.getName())){
            productService.deleteByIdAndVendorUsername(productId, auth.getName());
        }
        else {
            return "redirect:/403";
        }
        return "redirect:/vendor/myproducts";
    }

    @GetMapping("/orderList")
    public String vendorOrders(Model model) {
        List<OrderInfo> orderInfoList = orderService.findAllOrderInfo();
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
            return "redirect:/vendor/orderList";
        }
        List<OrderDetailInfo> details = orderService.listOrderDetailInfos(orderId);
        orderInfo.setDetails(details);
        model.addAttribute("orderInfo", orderInfo);
        return "order";
    }

    @GetMapping("/order")
    public String changeOrderStatus( @RequestParam("orderId") Long orderId, @RequestParam String status) {
        Order order = null;
        if (orderId != null) {
            order = orderService.findOrderById(orderId);
        }
        if (order == null) {
            return "redirect:/vendor/orderList";
        }
        if(status.equals(Order.PENDING) || status.equals(Order.IN_PROGRESS) || status.equals(Order.DELIVERED)){
            order.setStatus(status);
        }
        order.setStatus(status);
        orderService.save(order);
        return "redirect:/vendor/orderInfo?orderId="+orderId;
    }
}
