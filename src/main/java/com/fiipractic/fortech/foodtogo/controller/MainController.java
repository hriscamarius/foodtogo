package com.fiipractic.fortech.foodtogo.controller;

import com.fiipractic.fortech.foodtogo.dto.CustomerDto;
import com.fiipractic.fortech.foodtogo.dto.ProductDto;
import com.fiipractic.fortech.foodtogo.dto.VendorDto;
import com.fiipractic.fortech.foodtogo.entity.Customer;
import com.fiipractic.fortech.foodtogo.entity.Product;
import com.fiipractic.fortech.foodtogo.entity.User;
import com.fiipractic.fortech.foodtogo.model.CartInfo;
import com.fiipractic.fortech.foodtogo.model.CustomerForm;
import com.fiipractic.fortech.foodtogo.model.CustomerInfo;
import com.fiipractic.fortech.foodtogo.model.ProductInfo;
import com.fiipractic.fortech.foodtogo.service.OrderServiceImpl;
import com.fiipractic.fortech.foodtogo.service.ProductServiceImpl;
import com.fiipractic.fortech.foodtogo.service.UserService;
import com.fiipractic.fortech.foodtogo.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderServiceImpl orderService;

    private ModelMapper modelMapper = new ModelMapper();

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

    @RequestMapping("/buyProduct")
    public String listProductHandler(HttpServletRequest request, RedirectAttributes redirAttrs,
                                     @RequestParam(value = "id", required = false) Long productId){
        Product product = null;
        if(productId != null){
            product = productService.findById(productId).get();
        }
        if(product!= null){
            CartInfo cartInfo = Utils.getCartInSession(request);
            if(!cartInfo.getCartLines().isEmpty()){
                String  firstCartLineRestaurantName = cartInfo.getCartLines().get(0).getProductInfo().getRestaurantName();
                if(!product.getVendor().getRestaurantName().equals(firstCartLineRestaurantName)){
                    redirAttrs.addFlashAttribute("message",
                            "You have to choose the same restaurant as the products in the cart: "+firstCartLineRestaurantName);
                    return "redirect:/productList";
                }
            }
            ProductInfo productInfo = modelMapper.map(product, ProductInfo.class);
            productInfo.setRestaurantName(product.getVendor().getRestaurantName());
            cartInfo.addProduct(productInfo, 1);
        }
        return "redirect:/shoppingCart";
    }

    @RequestMapping("/shoppingCartRemoveProduct")
    public String removeProductHandler(HttpServletRequest request, Model model,
                                       @RequestParam(value = "id", required = false) Long productId){
        Product product = null;
        if(productId != null){
            product = productService.findById(productId).get();
        }
        if(product!= null){
            CartInfo cartInfo = Utils.getCartInSession(request);
            ProductInfo productInfo = modelMapper.map(product, ProductInfo.class);
            cartInfo.removeProduct(productInfo);
        }
        return "redirect:/shoppingCart";
    }

    @PostMapping("/shoppingCart")
    public String shoppingCartUpdateQty(HttpServletRequest request, Model model,
                                        @ModelAttribute("cartForm") CartInfo cartForm) {

        CartInfo cartInfo = Utils.getCartInSession(request);
        cartInfo.updateQuantity(cartForm);
        return "redirect:/shoppingCart";
    }

    @GetMapping("/shoppingCart")
    public String shoppingCartHandler(HttpServletRequest request, Model model) {
        CartInfo myCart = Utils.getCartInSession(request);

        model.addAttribute("cartForm", myCart);
        return "shoppingCart";
    }

    @GetMapping("/shoppingCartCustomer")
    public String shoppingCartCustomerForm(HttpServletRequest request, Model model) {

        CartInfo cartInfo = Utils.getCartInSession(request);
        if (cartInfo.isEmpty()) {
            return "redirect:/shoppingCart";
        }
        CustomerForm customerForm = new CustomerForm();;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.isAuthenticated()){
            User user = userService.findByUsername(auth.getName());
            if(user instanceof Customer){
                Customer customer = (Customer) user;
                customerForm.setName(customer.getName());
                customerForm.setEmail(customer.getEmail());
                customerForm.setPhoneNo(customer.getPhoneNo());
                customerForm.setAddress(customer.getAddress());
            }
        }else{
            CustomerInfo customerInfo = cartInfo.getCustomerInfo();
            customerForm = new CustomerForm(customerInfo);
        }
        model.addAttribute("customerForm", customerForm);
        return "shoppingCartCustomer";
    }

    @PostMapping("/shoppingCartCustomer")
    public String shoppingCartCustomerSave(HttpServletRequest request, Model model,
                                           @ModelAttribute("customerForm") @Validated CustomerForm customerForm,
                                           BindingResult result,
                                           final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            customerForm.setValid(false);
            return "shoppingCartCustomer";
        }

        customerForm.setValid(true);
        CartInfo cartInfo = Utils.getCartInSession(request);
        CustomerInfo customerInfo = modelMapper.map(customerForm, CustomerInfo.class);
        cartInfo.setCustomerInfo(customerInfo);

        return "redirect:/shoppingCartConfirmation";
    }

    @GetMapping("/shoppingCartConfirmation")
    public String shoppingCartConfirmationReview(HttpServletRequest request, Model model) {
        CartInfo cartInfo = Utils.getCartInSession(request);
        if (cartInfo.isEmpty()) {
            return "redirect:/shoppingCart";
        } else if (!cartInfo.isValidCustomer()) {
            return "redirect:/shoppingCartCustomer";
        }
        model.addAttribute("myCart", cartInfo);
        return "shoppingCartConfirmation";
    }

    @PostMapping("/shoppingCartConfirmation")
    public String shoppingCartConfirmationSave(HttpServletRequest request, Model model) {
        CartInfo cartInfo = Utils.getCartInSession(request);
        if (cartInfo.isEmpty()) {
            return "redirect:/shoppingCart";
        } else if (!cartInfo.isValidCustomer()) {
            return "redirect:/shoppingCartCustomer";
        }
        try {
            orderService.saveOrder(cartInfo);
        } catch (Exception e) {

            return "shoppingCartConfirmation";
        }
        // Remove Cart from Session.
        Utils.removeCartInSession(request);
        // Store last cart.
        Utils.storeLastOrderedCartInSession(request, cartInfo);
        return "redirect:/shoppingCartFinalize";
    }

    @GetMapping("/shoppingCartFinalize")
    public String shoppingCartFinalize(HttpServletRequest request, Model model) {

        CartInfo lastOrderedCart = Utils.getLastOrderedCartInSession(request);

        if (lastOrderedCart == null) {
            return "redirect:/shoppingCart";
        }
        model.addAttribute("lastOrderedCart", lastOrderedCart);
        return "shoppingCartFinalize";
    }
}
