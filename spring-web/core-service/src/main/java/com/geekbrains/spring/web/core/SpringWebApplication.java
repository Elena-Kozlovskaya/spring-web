package com.geekbrains.spring.web.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@PropertySource("classpath:secret.properties")
@EnableCaching
@EnableAsync
public class SpringWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringWebApplication.class, args);
	}
}
