package com.geekbrains.spring.web.recommendation.services;

import com.geekbrains.spring.web.api.dto.AnalyticItemDto;
import com.geekbrains.spring.web.api.recommendation.RecommendationDetailsDto;
import com.geekbrains.spring.web.recommendation.integrations.CartServiceIntegration;
import com.geekbrains.spring.web.recommendation.integrations.OrderServiceIntegration;
import com.geekbrains.spring.web.recommendation.models.Recommendation;
import com.geekbrains.spring.web.recommendation.models.RecommendationItem;
import com.geekbrains.spring.web.recommendation.repositories.RecommendationItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final OrderServiceIntegration orderServiceIntegration;
    private final CartServiceIntegration cartServiceIntegration;
    private final RecommendationItemsRepository recommendationItemsRepository;

    /*@Transactional
    public void createByOrders(RecommendationDetailsDto recommendationDetailsDto) {
        List<AnalyticItemDto> analyticItemDtos = orderServiceIntegration.findByDate(recommendationDetailsDto);
        createRecommendation(analyticItemDtos);
    }*/

    @Transactional
    @Scheduled(cron = "0 0 00 * * *")
    public void getAllOrdersByDay() {
        RecommendationDetailsDto recommendationDetailsDto = new RecommendationDetailsDto(LocalDate.now().minusDays(1L).toString(), LocalDate.now().toString());
        List<AnalyticItemDto> analyticItemDtos = orderServiceIntegration.findByDate(recommendationDetailsDto);
        createRecommendation(analyticItemDtos);
    }

    public void createByCarts(RecommendationDetailsDto recommendationDetailsDto) {
        List<AnalyticItemDto> analyticItemDtos = cartServiceIntegration.findByDate(recommendationDetailsDto).getItems();
        createRecommendation(analyticItemDtos);
    }

    @Transactional
    public void createRecommendation(List<AnalyticItemDto> analyticItemDtos){
        Recommendation recommendation = new Recommendation();
        for (AnalyticItemDto analyticItemDto : analyticItemDtos) {
            recommendation.add(analyticItemDto);
        }
        List<RecommendationItem> recommendationItems = recommendation.getRecommendations().stream()
                .sorted(Comparator.comparing(RecommendationItem::getQuantity).reversed())
                .limit(5)
                .collect(Collectors.toList());
        //сюда кэш
        recommendationItemsRepository.saveAll(recommendationItems);
    }
}