package com.geekbrains.spring.web.api.recommendation;

import com.geekbrains.spring.web.api.carts.CartItemDto;

import java.util.List;

public class RecommendationDto {
    private List<CartItemDto> items;
    private int totalPrice;


    public RecommendationDto(List<CartItemDto> items, int totalPrice) {
        this.items = items;
        this.totalPrice = totalPrice;
    }

    public RecommendationDto() {
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
