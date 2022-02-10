package com.geekbrains.spring.web.api.carts;

import java.util.List;

public class CartDto {
    private List<CartItemDto> items;
    private int totalPrice;


    public CartDto(List<CartItemDto> items, int totalPrice) {
        this.items = items;
        this.totalPrice = totalPrice;
    }

    public CartDto() {
    }

    public List<CartItemDto> getItems() {
        return items;
    }

    public int getTotalPrice() {
        return totalPrice;
    }


    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "CartDto{" +
                "items=" + items +
                ", totalPrice=" + totalPrice +
                '}';
    }

}
