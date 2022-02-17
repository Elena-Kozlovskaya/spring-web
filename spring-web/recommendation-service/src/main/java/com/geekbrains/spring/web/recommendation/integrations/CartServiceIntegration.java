package com.geekbrains.spring.web.recommendation.integrations;

import com.geekbrains.spring.web.api.carts.AnalyticCartDto;
import com.geekbrains.spring.web.api.recommendation.RecommendationDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    //сетевое взаимодействие
    private final WebClient cartServiceWebClient;

    public AnalyticCartDto findByDate(RecommendationDetailsDto recommendationDetailsDto) {
        String date = recommendationDetailsDto.getStartDate();
        AnalyticCartDto analyticCartDto = Objects.requireNonNull(cartServiceWebClient
                .get()
                .uri("/api/v1/cart/analytics/{date}", date)
                .retrieve()
                .bodyToMono(AnalyticCartDto.class)
                .block());
        return analyticCartDto;
    }

}
