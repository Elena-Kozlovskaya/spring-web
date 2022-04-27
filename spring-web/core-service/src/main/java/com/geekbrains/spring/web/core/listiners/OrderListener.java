package com.geekbrains.spring.web.core.listiners;

import com.geekbrains.spring.web.core.entities.OrderEvent;
import com.geekbrains.spring.web.core.integrations.CartServiceIntegration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

// Не поняла, где в данном приложении реально мог бы пригодиться Listener,
// поэтому передала ему в обработку очистку корзины юзера после создания заказа

@Component
@RequiredArgsConstructor
public class OrderListener {
    private final CartServiceIntegration cartServiceIntegration;

    @Async
    @EventListener
    public void handleOrderEvent(OrderEvent event) {
        cartServiceIntegration.clearUserCart(event.getMessage());
    }
}