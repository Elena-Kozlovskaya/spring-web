package com.geekbrains.spring.web.cart.integrations;

import com.geekbrains.spring.web.api.core.ProductDto;
import com.geekbrains.spring.web.api.exceptions.ProductsServiceAppError;
import com.geekbrains.spring.web.cart.exceptions.ProductsServiceIntegrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class ProductsServiceIntegration {
    /*private final RestTemplate restTemplate;

    @Value("${integrations.core-service.url}")
    private String productsServiceUrl;

    // могут быть ошибки 404, на выходе 500 - нужно обработать (кастомизировать ошибки и преобразовать к ProductServiceIntegrationException)
    public Optional<ProductDto> findById(Long id){
        ProductDto productDto = restTemplate.getForObject(productsServiceUrl + "/api/v1/products/" + id, ProductDto.class);
        return Optional.ofNullable(productDto);
    }*/

    private final WebClient productServiceWebClient;

    public ProductDto findById(Long id){
        ProductDto product = productServiceWebClient.get()
                .uri("/api/v1/products/{id}", id)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.is4xxClientError(),
                        clientResponse -> clientResponse.bodyToMono(ProductsServiceAppError.class).map(
                                body -> {
                                    if(body.getCode().equals(ProductsServiceAppError.ProductsServiceError.PRODUCT_NOT_FOUND.name())) {
                                        return new ProductsServiceIntegrationException("Выполнен некорректный запрос к сервису продуктов: продукт не найден");
                                    }
                                        if(body.getCode().equals(ProductsServiceAppError.ProductsServiceError.PRODUCTS_SERVICE_IS_BROKEN.name())){
                                            return  new ProductsServiceIntegrationException("Выполнен некорректный запрос к сервису продуктов: сервис продуктов сломан");
                                    }
                                        return new ProductsServiceIntegrationException("Выполнен некорректный запрос к сервису продуктов: причина не известна");
                                }
                        )
                )
                .bodyToMono(ProductDto.class)
                .block();
        return product;
    }

}
