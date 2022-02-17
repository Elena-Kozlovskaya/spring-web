package com.geekbrains.spring.web.recommendation.models;

import com.geekbrains.spring.web.api.dto.AnalyticItemDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Recommendation {
    private List<RecommendationItem> recommendations;
    private LocalDateTime localDateTime;


    public Recommendation() {
        this.recommendations = new ArrayList<>();
    }


        public void add(AnalyticItemDto analyticItemDto) {
            if (isAdded(analyticItemDto)) {
                return;
            }
            recommendations.add(new RecommendationItem(analyticItemDto));
        }

        public boolean isAdded(AnalyticItemDto analyticItemDto) {
            for (RecommendationItem r : recommendations) {
                if (r.getProductId().equals(analyticItemDto.getProductId())) {
                    r.changeQuantity(analyticItemDto.getQuantity());
                    return true;
                }
            }
            return false;
        }
}
