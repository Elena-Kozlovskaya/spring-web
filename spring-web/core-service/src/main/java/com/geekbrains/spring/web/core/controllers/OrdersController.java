package com.geekbrains.spring.web.core.controllers;

import com.geekbrains.spring.web.api.core.OrderDetailsDto;
import com.geekbrains.spring.web.api.core.OrderDto;
import com.geekbrains.spring.web.api.dto.AnalyticItemDto;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.api.recommendation.RecommendationDetailsDto;
import com.geekbrains.spring.web.core.converters.AnalyticItemConverter;
import com.geekbrains.spring.web.core.converters.OrderConverter;
import com.geekbrains.spring.web.core.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Заказы", description = "Методы работы с заказами")
public class OrdersController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;
    private final AnalyticItemConverter analyticItemConverter;


    @Operation(
            summary = "Запрос на создание нового заказа",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader @Parameter(description = "Имя пользователя", required = true) String username, @RequestBody @Parameter(description = "Данные для создания заказа", required = true) OrderDetailsDto orderDetailsDto) {
        orderService.createOrder(username, orderDetailsDto);
    }


    @Operation(
            summary = "Запрос на получение всех заказов пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = OrderDto.class))
                    )
            }
    )
    @GetMapping
    public List<OrderDto> getCurrentUserOrders(@RequestHeader @Parameter(description = "Имя пользователя", required = true) String username) {
        return orderService.findOrdersByUsername(username).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }


    @Operation(
            summary = "Запрос на получение списка заказов для сервиса аналитики",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = List.class))
                    )
            }
    )
    @PostMapping("/date")
    public List<AnalyticItemDto> getAllOrders(@RequestBody @Parameter(description = "Данные для выборки заказов", required = true) RecommendationDetailsDto recommendationDetailsDto) {
        LocalDateTime startedAt = LocalDateTime.parse(recommendationDetailsDto.getStartDate());
        LocalDateTime finishedAt = LocalDateTime.parse(recommendationDetailsDto.getFinishDate());
        return orderService.findAllOrdersByDate(startedAt, finishedAt).stream()
                .map(analyticItemConverter::entityToDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id){
        return orderConverter.entityToDto(orderService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found")));
    }
}
