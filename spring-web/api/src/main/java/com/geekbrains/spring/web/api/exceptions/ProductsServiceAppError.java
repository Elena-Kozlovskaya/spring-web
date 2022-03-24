package com.geekbrains.spring.web.api.exceptions;

public class ProductsServiceAppError extends AppError {
    public enum ProductsServiceError{
        PRODUCTS_SERVICE_IS_BROKEN, PRODUCT_NOT_FOUND
    }

    public ProductsServiceAppError(String code, String message) {
        super(code, message);
    }
}
