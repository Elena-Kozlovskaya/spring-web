package com.geekbrains.spring.web.cart.converters;

import com.geekbrains.spring.web.api.dto.CartDto;
import com.geekbrains.spring.web.cart.dto.Cart;
import org.springframework.stereotype.Component;

@Component
public class CartConverter {

    public CartDto entityToDto(Cart cart) {
        return new CartDto(cart.getItems(), cart.getTotalPrice());
    }
}
