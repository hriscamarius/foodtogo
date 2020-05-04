package com.fiipractic.fortech.foodtogo.service;

import com.fiipractic.fortech.foodtogo.entity.*;
import com.fiipractic.fortech.foodtogo.model.*;
import com.fiipractic.fortech.foodtogo.repository.OrderDetailRepository;
import com.fiipractic.fortech.foodtogo.repository.OrderRepository;
import com.fiipractic.fortech.foodtogo.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class OrderServiceImpl {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;
    private ModelMapper modelMapper = new ModelMapper();

    @Transactional(rollbackOn = Exception.class)
    public void saveOrder(CartInfo cartInfo){
        int orderNum = this.getMaxOrderNum() + 1;
        Order order = new Order();
        order.setOrderNum(orderNum);
        order.setOrderDate(new Date());
        order.setAmount(cartInfo.getAmountTotal());
        CustomerInfo customerInfo = cartInfo.getCustomerInfo();
        order.setCustomerName(customerInfo.getName());
        order.setCustomerAddress(customerInfo.getAddress());
        order.setCustomerEmail(customerInfo.getEmail());
        order.setCustomerPhone(customerInfo.getPhoneNo());
        order.setStatus(Order.PENDING);
        Product firstProductFromCart = productRepository.findById(cartInfo.getCartLines().get(0).getProductInfo().getId()).get();
        if(firstProductFromCart!=null){
            order.setVendor(firstProductFromCart.getVendor());
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.isAuthenticated()) {
            User user = userService.findByUsername(auth.getName());
            if (user instanceof Customer) {
                Customer customer = (Customer) user;
                order.setCustomer(customer);
            }
        }
        orderRepository.save(order);

        List<CartLineInfo> lines = cartInfo.getCartLines();
        for (CartLineInfo line:lines) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setAmount(line.getAmount());
            detail.setPrice(line.getProductInfo().getPrice());
            detail.setQuantity(line.getQuantity());
            Product product = productRepository.findById(line.getProductInfo().getId()).get();
            detail.setProduct(product);
            orderDetailRepository.save(detail);
        }
        cartInfo.setOrderNum(orderNum);
    }

    private int getMaxOrderNum() {
        Integer maxOrderNum = orderRepository.maxOrderNum();
        if(maxOrderNum == null){
            return 0;
        }
        return maxOrderNum;
    }

    public OrderInfo getOrderInfo(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        if(order == null){return null;}
        return modelMapper.map(order, OrderInfo.class);
    }

    public List<OrderDetailInfo> listOrderDetailInfos(Long orderId) {
        List<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrderId(orderId);
        List<OrderDetailInfo> orderDetailInfoList = new ArrayList<>();
        if(!orderDetailList.isEmpty()){
            for (OrderDetail orderDetail: orderDetailList) {
                if(productRepository.findById(orderDetail.getProduct().getId()).isPresent()){
                    OrderDetailInfo orderDetailInfo = new OrderDetailInfo();
                    orderDetailInfo.setId(orderDetail.getId());
                    orderDetailInfo.setProductId(orderDetail.getProduct().getId());
                    orderDetailInfo.setProductName(orderDetail.getProduct().getName());
                    orderDetailInfo.setQuanity(orderDetail.getQuantity());
                    orderDetailInfo.setPrice(orderDetail.getPrice());
                    orderDetailInfo.setAmount(orderDetail.getAmount());
                    orderDetailInfoList.add(orderDetailInfo);
                }
            }
        }
        return orderDetailInfoList;
    }

    public List<OrderInfo> findMyOrders(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        List<Order> orders = null;
        if(user instanceof Vendor){
            Vendor vendor = (Vendor) user;
            orders =  vendor.getVendorOrders();
        }
        if(user instanceof Customer){
            Customer customer = (Customer) user;
            orders = customer.getCustomerOrders();

        }
        List<OrderInfo> orderInfos = new ArrayList<>();
        for (Order order: orders) {
            OrderInfo orderInfo  =  new OrderInfo();
            orderInfo.setId(order.getId());
            orderInfo.setOrderDate(order.getOrderDate());
            orderInfo.setOrderNum(order.getOrderNum());
            orderInfo.setStatus(order.getStatus());
            orderInfo.setAmount(order.getAmount());
            orderInfo.setCustomerName(order.getCustomerName());
            orderInfo.setCustomerAddress(order.getCustomerAddress());
            orderInfo.setCustomerEmail(order.getCustomerEmail());
            orderInfo.setCustomerPhone(order.getCustomerPhone());
            if(order.getVendor()!=null){
                orderInfo.setRestaurantName(order.getVendor().getRestaurantName());
            }else {orderInfo.setRestaurantName("Not availabe");}
            orderInfos.add(orderInfo);
        }
       return orderInfos;
    }

    public List<OrderInfo> findAll(){
        List<Order> orders = (List<Order>) orderRepository.findAll();
        List<OrderInfo> orderInfos = new ArrayList<>();
        for (Order order: orders) {
            OrderInfo orderInfo  =  new OrderInfo();
            orderInfo.setId(order.getId());
            orderInfo.setOrderDate(order.getOrderDate());
            orderInfo.setOrderNum(order.getOrderNum());
            orderInfo.setStatus(order.getStatus());
            orderInfo.setAmount(order.getAmount());
            orderInfo.setCustomerName(order.getCustomerName());
            orderInfo.setCustomerAddress(order.getCustomerAddress());
            orderInfo.setCustomerEmail(order.getCustomerEmail());
            orderInfo.setCustomerPhone(order.getCustomerPhone());
            if(order.getVendor()!=null){
                orderInfo.setRestaurantName(order.getVendor().getRestaurantName());
            }else {orderInfo.setRestaurantName("Not availabe");}
            orderInfos.add(orderInfo);
        }
        return orderInfos;
    }

    public OrderInfo findOrderInfoById(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        OrderInfo  orderInfo = new OrderInfo();
        orderInfo.setId(order.getId());
        orderInfo.setOrderDate(order.getOrderDate());
        orderInfo.setOrderNum(order.getOrderNum());
        orderInfo.setStatus(order.getStatus());
        orderInfo.setAmount(order.getAmount());
        orderInfo.setCustomerName(order.getCustomerName());
        orderInfo.setCustomerAddress(order.getCustomerAddress());
        orderInfo.setCustomerEmail(order.getCustomerEmail());
        orderInfo.setCustomerPhone(order.getCustomerPhone());
        if(order.getVendor()!=null){
            orderInfo.setRestaurantName(order.getVendor().getRestaurantName());
        }else {orderInfo.setRestaurantName("Not availabe");}
        return orderInfo;
    }

    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).get();
    }

    public void save(Order order) {
        orderRepository.save(order);
    }
}
