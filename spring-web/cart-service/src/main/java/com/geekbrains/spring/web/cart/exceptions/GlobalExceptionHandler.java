package com.geekbrains.spring.web.cart.exceptions;

import com.geekbrains.spring.web.api.exceptions.CartServiceAppError;
import com.geekbrains.spring.web.api.exceptions.ProductsServiceAppError;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<CartServiceAppError> catchResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new CartServiceAppError(CartServiceAppError.CartServiceError.CART_IS_BROKEN.name(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<CartServiceAppError> catchCartIsBrokenException(CartIsBrokenException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new CartServiceAppError(CartServiceAppError.CartServiceError.CART_IS_BROKEN.name(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ProductsServiceAppError> catchProductsIntegrationException(ProductsServiceIntegrationException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ProductsServiceAppError(ProductsServiceAppError.ProductsServiceError.PRODUCTS_SERVICE_IS_BROKEN.name(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // сделать интеграцию через редис и переписать
    /*@ExceptionHandler
    public ResponseEntity<ProductsServiceAppError> catchProductResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ProductsServiceAppError(ProductsServiceAppError.ProductsServiceError.PRODUCT_NOT_FOUND.name(), e.getMessage()), HttpStatus.NOT_FOUND);
    }*/
}
