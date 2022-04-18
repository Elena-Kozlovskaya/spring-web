package com.geekbrains.spring.web.recommendation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Доработать:
//1. Сделать жесткие временные рамки (30 дней, 1 день)
//2. Сделать накопление обработанных данных статистики и выдача их по запросу

@SpringBootApplication
public class SpringWebRecommendationApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringWebRecommendationApplication.class, args);
	}
}
