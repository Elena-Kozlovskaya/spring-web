package com.geekbrains.spring.web.cart.exceptions;


public class ProductsServiceIntegrationException extends RuntimeException {
    public ProductsServiceIntegrationException(String message){
        super(message);
    }
}
