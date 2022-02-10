package com.geekbrains.spring.web.cart;

import com.geekbrains.spring.web.api.core.ProductDto;
import com.geekbrains.spring.web.cart.services.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class CartTest {
    private static ProductDto firstProduct;
    private static ProductDto secondProduct;

    @Autowired
    private CartService cartService;

    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    public void initCart() {
        cartService.clearCart("test_cart");
    }

    @BeforeAll
    public static void createProductDto(){
        firstProduct = new ProductDto(1L, "Tomato", 20, "Vegetables");
        secondProduct = new ProductDto(2L, "Orange", 40, "Fruits");
    }

    @Test
    public void addToCartTest(){
        Mockito.doReturn(firstProduct).when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + 1L, ProductDto.class);
        cartService.addToCart("test_cart", 1L);
        cartService.addToCart("test_cart", 1L);
        cartService.addToCart("test_cart", 1L);
        cartService.addToCart("test_cart", 1L);
        Assertions.assertEquals(1, cartService.getCurrentCart("test_cart").getItems().size());
        Assertions.assertEquals(80, cartService.getCurrentCart("test_cart").getTotalPrice());
        Assertions.assertEquals(4, cartService.getCurrentCart("test_cart").getItems().stream().map(p -> p.getQuantity()).findFirst().get());
        Mockito.doReturn(secondProduct).when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + 2L, ProductDto.class);
        cartService.addToCart("test_cart", 2L);
        Assertions.assertEquals(2, cartService.getCurrentCart("test_cart").getItems().size());
        Assertions.assertEquals(120, cartService.getCurrentCart("test_cart").getTotalPrice());
        Assertions.assertEquals(2, cartService.getCurrentCart("test_cart").getItems().stream().map(p -> p.getProductId()).count());
    }

    @Test
    public void removeItemFromCartTest(){
        Mockito.doReturn(firstProduct).when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + 1L, ProductDto.class);
        cartService.addToCart("test_cart", 1L);
        cartService.addToCart("test_cart", 1L);
        Mockito.doReturn(secondProduct).when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + 2L, ProductDto.class);
        cartService.addToCart("test_cart", 2L);
        Assertions.assertEquals(2, cartService.getCurrentCart("test_cart").getItems().size());
        cartService.removeItemFromCart("test_cart", 1L);
        Assertions.assertEquals(1, cartService.getCurrentCart("test_cart").getItems().size());
    }

    @Test
    public void decrementItemTest(){
        Mockito.doReturn(firstProduct).when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + 1L, ProductDto.class);
        cartService.addToCart("test_cart", 1L);
        cartService.addToCart("test_cart", 1L);
        cartService.addToCart("test_cart", 1L);
        cartService.addToCart("test_cart", 1L);
        Assertions.assertEquals(4, cartService.getCurrentCart("test_cart").getItems().stream().map(p -> p.getQuantity()).findFirst().get());
        cartService.decrementItem("test_cart", 1L);
        Assertions.assertEquals(3, cartService.getCurrentCart("test_cart").getItems().stream().map(p -> p.getQuantity()).findFirst().get());
    }
    @Test
    public void mergeTest(){
        cartService.clearCart("user_cart");
        Assertions.assertEquals(0, cartService.getCurrentCart("test_cart").getTotalPrice());
        Assertions.assertEquals(0, cartService.getCurrentCart("user_cart").getTotalPrice());
        Mockito.doReturn(firstProduct).when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + 1L, ProductDto.class);
        cartService.addToCart("test_cart", 1L);
        cartService.addToCart("test_cart", 1L);
        Mockito.doReturn(secondProduct).when(restTemplate).getForObject("http://localhost:5555/core/api/v1/products/" + 2L, ProductDto.class);
        cartService.addToCart("user_cart", 2L);
        cartService.merge("user_cart", "test_cart");
        Assertions.assertEquals(2, cartService.getCurrentCart("user_cart").getItems().size());
        Assertions.assertEquals(80, cartService.getCurrentCart("user_cart").getTotalPrice());
        Assertions.assertEquals(0, cartService.getCurrentCart("test_cart").getItems().size());
    }
}
