package com.geekbrains.spring.web.cart.controllers;

import com.geekbrains.spring.web.api.carts.AnalyticCartDto;
import com.geekbrains.spring.web.api.carts.CartDto;
import com.geekbrains.spring.web.api.dto.StringResponse;
import com.geekbrains.spring.web.api.exceptions.CartServiceAppError;
import com.geekbrains.spring.web.api.exceptions.ProductsServiceAppError;
import com.geekbrains.spring.web.cart.converters.AnalyticCartConverter;
import com.geekbrains.spring.web.cart.converters.CartConverter;
import com.geekbrains.spring.web.cart.exceptions.ProductsServiceIntegrationException;
import com.geekbrains.spring.web.cart.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Tag(name = "Корзина", description = "Методы работы с корзиной")
public class CartsController {
    private final CartService cartService;
    private final CartConverter cartConverter;
    private final AnalyticCartConverter analyticCartConverter;

    @Operation(
            summary = "Запрос на получение корзины по uuid",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = CartDto.class))
                    ),
                    @ApiResponse(
                            description = "Сервис корзины недоступен", responseCode = "500",
                            content = @Content(schema = @Schema(implementation = CartServiceAppError.class))
                    )
            }
    )
    @GetMapping("/{uuid}")
    public CartDto getCart(@RequestHeader(required = false) @Parameter(description = "Имя пользователя", required = false) String username,
                           @PathVariable @Parameter(description = "Идентификатор пользователя", required = true) String uuid) {
        /*if(10 < 20){
            throw new CartIsBrokenException("Корзина сломана");
        } // проверка эксепшена */
        return cartConverter.modelToDto(cartService.getCurrentCart(getCurrentCartUuid(username, uuid)));
    }

    @Operation(
            summary = "Запрос на генерацию новой корзины",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = StringResponse.class))
                    ),
                    @ApiResponse(
                            description = "Сервис корзины недоступен", responseCode = "500",
                            content = @Content(schema = @Schema(implementation = CartServiceAppError.class))
                    )
            }
    )
    @GetMapping("/generate")
    public StringResponse getCart() {
        return new StringResponse(cartService.generateCartUuid());
    }


    @Operation(
            summary = "Запрос на добавление продукта в корзину по uuid или имени пользователя и productId ",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Продукт не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ProductsServiceAppError.class))
                    ),
                    @ApiResponse(
                            description = "Сервис продуктов недоступен", responseCode = "500",
                            content = @Content(schema = @Schema(implementation = ProductsServiceIntegrationException.class))
                    )
            }
    )
    @GetMapping("/{uuid}/add/{productId}")
    public void add(@RequestHeader(required = false) @Parameter(description = "Имя пользователя", required = false) String username,
                    @PathVariable @Parameter(description = "Идентификатор пользователя", required = true) String uuid,
                    @PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long productId) {
        cartService.addToCart(getCurrentCartUuid(username, uuid), productId);
    }


    @Operation(
            summary = "Запрос на уменьшение количества продукта в корзине (на 1 шт) по uuid пользователя и productId ",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Сервис корзины недоступен", responseCode = "500",
                            content = @Content(schema = @Schema(implementation = CartServiceAppError.class))
                    )
            }
    )
    @GetMapping("/{uuid}/decrement/{productId}")
    public void decrement(@RequestHeader(required = false) @Parameter(description = "Имя пользователя", required = false) String username,
                          @PathVariable @Parameter(description = "Идентификатор пользователя", required = true) String uuid,
                          @PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long productId) {
        cartService.decrementItem(getCurrentCartUuid(username, uuid), productId);
    }


    @Operation(
            summary = "Запрос на удаление продукта из корзины по uuid пользователя и productId ",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Сервис корзины недоступен", responseCode = "500",
                            content = @Content(schema = @Schema(implementation = CartServiceAppError.class))
                    )
            }
    )
    @GetMapping("/{uuid}/remove/{productId}")
    public void remove(@RequestHeader(required = false) @Parameter(description = "Имя пользователя", required = false) String username,
                       @PathVariable @Parameter(description = "Идентификатор пользователя", required = true) String uuid,
                       @PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long productId) {
        cartService.removeItemFromCart(getCurrentCartUuid(username, uuid), productId);
    }


    @Operation(
            summary = "Запрос на очистку корзины по uuid пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Сервис корзины недоступен", responseCode = "500",
                            content = @Content(schema = @Schema(implementation = CartServiceAppError.class))
                    )
            }
    )
    @GetMapping("/{uuid}/clear")
    public void clear(@RequestHeader(required = false) @Parameter(description = "Имя пользователя", required = false) String username,
                      @PathVariable @Parameter(description = "Идентификатор пользователя", required = true) String uuid) {
        cartService.clearCart(getCurrentCartUuid(username, uuid));
    }

    @Operation(
            summary = "Запрос на объединение корзины гостя и аутентифицированного пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Сервис корзины недоступен", responseCode = "500",
                            content = @Content(schema = @Schema(implementation = CartServiceAppError.class))
                    )
            }
    )
    @GetMapping("/{uuid}/merge")
    public void merge(@RequestHeader @Parameter(description = "Имя пользователя") String username,
                      @PathVariable @Parameter(description = "Идентификатор пользователя") String uuid) {
        cartService.merge(
                getCurrentCartUuid(username, null),
                getCurrentCartUuid(null, uuid)
        );
    }

    private String getCurrentCartUuid(String username, String uuid) {
        if (username != null) {
            return cartService.getCartUuidFromSuffix(username);
        }
        return cartService.getCartUuidFromSuffix(uuid);
    }

    // доработать сервис аналитики, сделать разметку метода
    @GetMapping("/analytics/{date}")
    public AnalyticCartDto getAnalytics(@PathVariable String date) {
        return analyticCartConverter.modelToDto(cartService.getAnalyticsByDate(getAnalyticsByDate(date)));
    }

    private String getAnalyticsByDate(String date) {
        return cartService.getAnalyticsUuidFromSuffix(date);
    }
}
