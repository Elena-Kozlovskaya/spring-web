package com.geekbrains.spring.web.core.controllers;

import com.geekbrains.spring.web.api.core.ProductDto;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.core.converters.ProductConverter;
import com.geekbrains.spring.web.core.entities.Product;
import com.geekbrains.spring.web.core.services.ProductsService;
import com.geekbrains.spring.web.core.validators.ProductValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Продукты", description = "Методы работы с продуктами")

public class ProductsController {
    private final ProductsService productsService;
    private final ProductConverter productConverter;
    private final ProductValidator productValidator;

    @Operation(
            summary = "Запрос на получение страницы продуктов",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Page.class))
                    )
            }

    )
    @GetMapping
    public Page<ProductDto> getAllProducts(
            @RequestParam(name = "p", defaultValue = "1") @Parameter(description = "Номер страницы") Integer page,
            @RequestParam(name = "min_price", required = false) @Parameter(description = "Минимальная цена") Integer minPrice,
            @RequestParam(name = "max_price", required = false) @Parameter(description = "Максимальная цена") Integer maxPrice,
            @RequestParam(name = "title_part", required = false) @Parameter(description = "Часть названия продукта") String titlePart,
            @RequestParam(name = "category_part_title", required = false) @Parameter(description = "Часть названия категории") String categoryPartTitle
            ) {
        if (page < 1) {
            page = 1;
        }
        return productsService.findAll(minPrice, maxPrice, titlePart, categoryPartTitle, page)
                .map(p -> productConverter.entityToDto(p)
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Запрос на получение продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),
                    /*@ApiResponse(
                            description = "Ошибка пользователя", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = CartServiceAppError.class))
                    )*/
            }

    )
    public ProductDto getProductById(@PathVariable @Parameter(description = "Иденификатор продукта", required = true) Long id) {
        Product product = productsService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));
        return productConverter.entityToDto(product);
    }

    @Operation(
            summary = "Запрос на добавление нового продукта",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            }

    )
    @PostMapping
    public ProductDto saveNewProduct(@RequestBody @Parameter(description = "Dto продукта", required = true) ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productConverter.dtoToEntity(productDto);
        product = productsService.save(product);
        return productConverter.entityToDto(product);
    }

    @Operation(
            summary = "Запрос на изменение существующего продукта",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            }

    )
    @PutMapping
    public ProductDto updateProduct(@RequestBody @Parameter(description = "Dto продукта", required = true) ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productsService.update(productDto);
        return productConverter.entityToDto(product);
    }

    @Operation(
            summary = "Запрос на удаление продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }

    )
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable @Parameter(description = "ID продукта", required = true) Long id) {
        productsService.deleteById(id);
    }
}
