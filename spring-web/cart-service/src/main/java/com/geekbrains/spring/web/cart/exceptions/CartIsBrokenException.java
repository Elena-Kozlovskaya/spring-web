package com.geekbrains.spring.web.cart.exceptions;

//тест эксепшена интеграции корзины в кор сервисе
public class CartIsBrokenException extends RuntimeException {
    public CartIsBrokenException (String message){
        super(message);
    }
}
