package com.fiipractic.fortech.foodtogo.controller;

import com.fiipractic.fortech.foodtogo.entity.Customer;
import com.fiipractic.fortech.foodtogo.model.OrderDetailInfo;
import com.fiipractic.fortech.foodtogo.model.OrderInfo;
import com.fiipractic.fortech.foodtogo.service.OrderServiceImpl;
import com.fiipractic.fortech.foodtogo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@RequestMapping("/customer")
@Transactional
public class CustomerController {

    @Autowired
    private UserService userService;
    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping("/profile")
    public String customerProfile(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken)){
            Customer customer = (Customer) userService.findByUsername(auth.getName());
            model.addAttribute("customer", customer);
        }
        return "customer/profile";
    }

    @GetMapping("/orderList")
    public String customerOrders(Model model) {
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
            return "redirect:/customer/orderList";
        }
        List<OrderDetailInfo> details = orderService.listOrderDetailInfos(orderId);
        orderInfo.setDetails(details);
        model.addAttribute("orderInfo", orderInfo);
        return "order";
    }
}
