package com.fiipractic.fortech.foodtogo.service;

import com.fiipractic.fortech.foodtogo.dto.ProductDto;
import com.fiipractic.fortech.foodtogo.dto.ProductRegistrationDto;
import com.fiipractic.fortech.foodtogo.entity.Product;
import com.fiipractic.fortech.foodtogo.entity.Vendor;
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
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;
    private ModelMapper modelMapper = new ModelMapper();

    public List<ProductDto> findAllProductDtos() {
        List<Product> products = (List<Product>) productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product: products) {
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            productDto.setRestaurantName(product.getVendor().getRestaurantName());
            productDtos.add(productDto);
        }
        return productDtos;
    }

    public List<Product> findAllProducts(){
        return (List<Product>) productRepository.findAll();
    }

    public Product findByNameAndVendorId(String name, Long vendorId) {
        return productRepository.findByNameAndVendorId(name, vendorId);
    }

    public boolean existsByNameAndVendorUsername(String name, String username) {
        return productRepository.existsByNameAndVendorUsername(name, username);
    }

    public boolean existsByNameAndVendorId(String name, Long vendorId){
        return productRepository.existsByNameAndVendorId(name, vendorId);
    }

    public void save(ProductRegistrationDto productRegistrationDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.isAuthenticated()){
            Vendor vendor = (Vendor) userService.findByUsername(auth.getName());
            if(vendor!=null){
                Product product = modelMapper.map(productRegistrationDto, Product.class);
                product.setVendor(vendor);
                product.setCreateDate(new Date());
                productRepository.save(product);
            }
        }
    }

    public void deleteById(Long productId) {
        productRepository.deleteById(productId);
    }

    public void deleteByIdAndVendorUsername(Long productId, String username) {
        productRepository.deleteByIdAndVendorUsername(productId, username);
    }

    public boolean existsByIdAndVendorUsername(Long productId, String username) {
        return productRepository.existsByIdAndVendorUsername(productId, username);
    }
}