package com.fiipractic.fortech.foodtogo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CartInfo {

    private int orderNum;
    private CustomerInfo customerInfo;
    private final List<CartLineInfo> cartLines = new ArrayList<CartLineInfo>();

    public CartLineInfo findLineById(Long productId){
        for (CartLineInfo line: this.cartLines) {
            if(line.getProductInfo().getId().equals(productId)){
                return line;
            }
        }
        return null;
    }
    public void addProduct(ProductInfo productInfo, int quantity){
        CartLineInfo line = this.findLineById(productInfo.getId());

        if(line == null){
            line = new CartLineInfo();
            line.setQuantity(0);
            line.setProductInfo(productInfo);
            this.cartLines.add(line);
        }
        int newQuantity = line.getQuantity() + quantity;
        if(newQuantity <= 0){
            this.cartLines.remove(line);
        }else {
            line.setQuantity(newQuantity);
        }
    }

    public void updateProduct(Long productId, int quantity){
        CartLineInfo line = this.findLineById(productId);

        if(line!=null){
            if(quantity<=0){
                this.cartLines.remove(line);
            } else {
                line.setQuantity(quantity);
            }
        }
    }

    public void removeProduct(ProductInfo productInfo){
        CartLineInfo line = this.findLineById(productInfo.getId());
        if(line != null){
            this.cartLines.remove(line);
        }
    }

    public boolean isEmpty(){
        return this.cartLines.isEmpty();
    }

    public boolean isValidCustomer() {
        return this.customerInfo != null && this.customerInfo.isValid();
    }

    public int getQuantityTotal(){
        int quantity = 0;
        for(CartLineInfo line : this.cartLines){
            quantity+=line.getQuantity();
        }
        return quantity;
    }

    public double getAmountTotal(){
        double total = 0;
        for(CartLineInfo line : this.cartLines){
            total+=line.getAmount();
        }
        return total;
    }

    public void updateQuantity(CartInfo cartInfo){
        if(cartInfo!=null){
            List<CartLineInfo> lines = cartInfo.getCartLines();
            for(CartLineInfo line : lines){
                this.updateProduct(line.getProductInfo().getId(), line.getQuantity());
            }
        }
    }
}

