package com.geekbrains.spring.web.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WebAuthServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebAuthServiceApplication.class, args);
	}
}
