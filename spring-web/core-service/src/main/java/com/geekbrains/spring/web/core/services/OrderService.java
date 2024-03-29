package com.geekbrains.spring.web.core.services;

import com.geekbrains.spring.web.api.carts.CartDto;
import com.geekbrains.spring.web.api.core.OrderDetailsDto;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.core.entities.Order;
import com.geekbrains.spring.web.core.entities.OrderItem;
import com.geekbrains.spring.web.core.enums.OrderStatus;
import com.geekbrains.spring.web.core.integrations.CartServiceIntegration;
import com.geekbrains.spring.web.core.repositories.OrderItemRepository;
import com.geekbrains.spring.web.core.repositories.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrdersRepository ordersRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartServiceIntegration cartServiceIntegration;
    private final ProductsService productsService;

    @Transactional
    public void createOrder(String username, OrderDetailsDto orderDetailsDto) {
        CartDto currentCart = cartServiceIntegration.getUserCart(username);
        /*Order order = new Order();
        order.setAddress(orderDetailsDto.getAddress());
        order.setCity(orderDetailsDto.getCity());
        order.setPostalCode(orderDetailsDto.getPostalCode());
        order.setPhone(orderDetailsDto.getPhone());
        order.setUsername(username);
        order.setTotalPrice(currentCart.getTotalPrice());
        order.setStatus(OrderStatus.NOT_PAID.name());*/
        Order order = Order.builder()
                .address(orderDetailsDto.getAddress())
                .city(orderDetailsDto.getCity())
                .postalCode(orderDetailsDto.getPostalCode())
                .phone(orderDetailsDto.getPhone())
                .username(username)
                .totalPrice(currentCart.getTotalPrice())
                .status(OrderStatus.NOT_PAID.name())
                .build();
        List<OrderItem> items = currentCart.getItems().stream()
                .map(o -> {
                    /*OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setQuantity(o.getQuantity());
                    item.setPricePerProduct(o.getPricePerProduct());
                    item.setPrice(o.getPrice());
                    item.setProduct(productsService.findById(o.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found")));*/
                    OrderItem item = OrderItem.builder()
                            .order(order)
                            .quantity(o.getQuantity())
                            .pricePerProduct(o.getPricePerProduct())
                            .price(o.getPrice())
                            .product(productsService.findById(o.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found")))
                            .build();
                    return item;
                }).collect(Collectors.toList());
        order.setItems(items);
        ordersRepository.save(order);
        cartServiceIntegration.clearUserCart(username);
    }

    public List<Order> findOrdersByUsername(String username) {
        return ordersRepository.findAllByUsername(username);
    }

    public List<OrderItem> findAllOrdersByDate(LocalDateTime createdAt, LocalDateTime finishedAt) {
        return orderItemRepository.findAllByDate(createdAt, finishedAt);
    }

    public Optional<Order> findById(Long id){
        return ordersRepository.findById(id);
    }

    public Optional<Order> findByIdWithStatus(Long id, String status){
        return ordersRepository.findByIdWithStatus(id, status);
    }

    @Transactional
    public void updateOrderStatus(Long id, String status){
        Order order = ordersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Заказ не найдет"));
        order.setStatus(status);
        ordersRepository.save(order);
    }
}
