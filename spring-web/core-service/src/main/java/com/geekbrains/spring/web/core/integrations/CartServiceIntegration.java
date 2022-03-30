package com.geekbrains.spring.web.core.integrations;

import com.geekbrains.spring.web.api.carts.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    //сетевое взаимодействие
    private final WebClient cartServiceWebClient;

    public void clearUserCart(String username) {
        cartServiceWebClient.get()
                .uri("/api/v1/cart/0/clear")// т.к. метод clear void, вернется ответ (статус) без тела (определенного объекта)
                .header("username", username)
                .retrieve()
                .toBodilessEntity() //ожидаем ответ (статус) без тела
                .block();
    }

    public CartDto getUserCart(String username) {
        CartDto cart = cartServiceWebClient.get()
                .uri("/api/v1/cart/0")
                .header("username", username)
                .retrieve()
                .bodyToMono(CartDto.class)// преобразуем к объекту моно (реактивное программирование) запрос послали и сразу освободили поток, когда ответ придет вернемся к этой задаче. Можно получить либо один (моно) либо много (флакс) объектов
                .block();// это ожидание объекта (синхронизация) - операция после ответа преобразуем к объекту и просто вернем объект без доп обработки
        return cart;
    }

}
