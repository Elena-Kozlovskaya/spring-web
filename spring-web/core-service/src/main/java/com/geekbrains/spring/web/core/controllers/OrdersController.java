package com.geekbrains.spring.web.core.controllers;

import com.geekbrains.spring.web.api.core.OrderDetailsDto;
import com.geekbrains.spring.web.api.core.OrderDto;
import com.geekbrains.spring.web.api.dto.AnalyticItemDto;
import com.geekbrains.spring.web.api.recommendation.RecommendationDetailsDto;
import com.geekbrains.spring.web.core.converters.AnalyticItemConverter;
import com.geekbrains.spring.web.core.converters.OrderConverter;
import com.geekbrains.spring.web.core.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor

public class OrdersController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;
    private final AnalyticItemConverter analyticItemConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String username, @RequestBody OrderDetailsDto orderDetailsDto) {
        orderService.createOrder(username, orderDetailsDto);
    }

    @GetMapping
    public List<OrderDto> getCurrentUserOrders(@RequestHeader String username) {
        return orderService.findOrdersByUsername(username).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }

    @PostMapping("/date")
    public List<AnalyticItemDto> getAllOrders(@RequestBody RecommendationDetailsDto recommendationDetailsDto) {
        LocalDateTime startedAt = LocalDateTime.parse(recommendationDetailsDto.getStartDate());
        LocalDateTime finishedAt = LocalDateTime.parse(recommendationDetailsDto.getFinishDate());
        return orderService.findAllOrdersByDate(startedAt, finishedAt).stream()
                .map(analyticItemConverter::entityToDto).collect(Collectors.toList());
    }
}
