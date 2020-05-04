package com.fiipractic.fortech.foodtogo.controller;

import com.fiipractic.fortech.foodtogo.entity.Customer;
import com.fiipractic.fortech.foodtogo.model.*;
import com.fiipractic.fortech.foodtogo.service.OrderServiceImpl;
import com.fiipractic.fortech.foodtogo.service.UserService;
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

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/customer")
@Transactional
public class CustomerController {

    @Autowired
    private UserService userService;
    @Autowired
    private OrderServiceImpl orderService;
    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/profile")
    public String customerProfile(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken)){
            Customer customer = (Customer) userService.findByUsername(auth.getName());
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            CustomerUpdateForm customerUpdateForm = modelMapper.map(customer, CustomerUpdateForm.class);
            model.addAttribute("customerUpdateForm", customerUpdateForm);
        }
        return "customer/profileCustomer";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute("customerUpdateForm") @Valid CustomerUpdateForm customerUpdateForm, BindingResult result){
        if(result.hasErrors()){
            return "customer/profileCustomer";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken)){
            Customer customer = (Customer) userService.findByUsername(auth.getName());
            userService.updateCustomer(customer.getId(), customerUpdateForm);
        }
        return "redirect:/customer/profile";
    }

    @GetMapping("/orderList")
    public String customerOrders(Model model) {
        List<OrderInfo> orderInfoList = orderService.findMyOrders();
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
            return "redirect:/customer/orderList";
        }
        List<OrderDetailInfo> details = orderService.listOrderDetailInfos(orderId);
        if(details!=null){
            orderInfo.setDetails(details);
            model.addAttribute("orderInfo", orderInfo);
        }
        return "order";
    }
}
