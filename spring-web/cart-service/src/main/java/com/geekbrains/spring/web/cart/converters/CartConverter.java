package com.geekbrains.spring.web.cart.converters;

import com.geekbrains.spring.web.api.carts.CartDto;
import com.geekbrains.spring.web.api.carts.CartItemDto;
import com.geekbrains.spring.web.cart.models.Cart;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

//mapper
@Component
public class CartConverter {

    public CartDto modelToDto(Cart cart) {
        List<CartItemDto> cartItems = cart.getItems().stream().map(item ->
                new CartItemDto(item.getProductId(), item.getProductTitle(), item.getQuantity(), item.getPricePerProduct(), item.getPrice())
        ).collect(Collectors.toList());
        return new CartDto(cartItems, cart.getTotalPrice());
    }
}
