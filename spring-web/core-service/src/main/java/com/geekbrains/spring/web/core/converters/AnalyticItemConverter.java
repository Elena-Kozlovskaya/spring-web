package com.geekbrains.spring.web.core.converters;


import com.geekbrains.spring.web.api.dto.AnalyticItemDto;
import com.geekbrains.spring.web.core.entities.OrderItem;
import org.springframework.stereotype.Component;

//mapper
@Component
public class AnalyticItemConverter {

    public AnalyticItemDto entityToDto(OrderItem orderItem) {
        return new AnalyticItemDto(orderItem.getProduct().getId(), orderItem.getProduct().getTitle(), orderItem.getQuantity());
    }
}
