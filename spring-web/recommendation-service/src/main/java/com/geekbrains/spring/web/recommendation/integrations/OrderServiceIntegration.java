package com.geekbrains.spring.web.recommendation.integrations;

import com.geekbrains.spring.web.api.dto.AnalyticItemDto;
import com.geekbrains.spring.web.api.recommendation.RecommendationDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class OrderServiceIntegration {
    private final WebClient orderServiceWebClient;


    public List<AnalyticItemDto> findByDate(RecommendationDetailsDto recommendationDetailsDto) {
        List<AnalyticItemDto> analyticItemDtos= Objects.requireNonNull(orderServiceWebClient
                        .post()
                        .uri("/api/v1/orders/date")
                        .syncBody(recommendationDetailsDto)
                        .retrieve()
                        .bodyToFlux(AnalyticItemDto.class)
                        .collectList()
                        .block());
            return analyticItemDtos;
    }
}
