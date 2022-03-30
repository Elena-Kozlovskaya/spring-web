package com.geekbrains.spring.web.cart.converters;

import com.geekbrains.spring.web.api.carts.AnalyticCartDto;
import com.geekbrains.spring.web.api.dto.AnalyticItemDto;
import com.geekbrains.spring.web.cart.models.AnalyticsCart;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AnalyticCartConverter {

    public AnalyticCartDto modelToDto(AnalyticsCart cart) {
        List<AnalyticItemDto> cartItems = cart.getItems().stream().map(item ->
                new AnalyticItemDto(item.getProductId(), item.getProductTitle(), item.getQuantity())
        ).collect(Collectors.toList());
        return new AnalyticCartDto(cartItems);
    }
}
