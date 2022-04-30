package com.geekbrains.spring.web.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CartWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(CartWebApplication.class, args);
	}
}
