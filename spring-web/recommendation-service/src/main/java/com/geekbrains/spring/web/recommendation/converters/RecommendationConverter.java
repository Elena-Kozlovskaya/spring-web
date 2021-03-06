package com.geekbrains.spring.web.recommendation.converters;

import com.geekbrains.spring.web.api.recommendation.RecommendationItemDto;
import com.geekbrains.spring.web.recommendation.models.RecommendationItem;
import org.springframework.stereotype.Component;

@Component
public class RecommendationConverter {
    public RecommendationItemDto modelToDto(RecommendationItem recommendationItem) {
        RecommendationItemDto recommendationItemDto = new RecommendationItemDto(recommendationItem.getProductId(), recommendationItem.getProductTitle(), recommendationItem.getQuantity());
        return recommendationItemDto;
    }
}
