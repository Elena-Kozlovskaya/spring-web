package com.geekbrains.spring.web.recommendation.controllers;

import com.geekbrains.spring.web.api.recommendation.RecommendationDetailsDto;
import com.geekbrains.spring.web.api.recommendation.RecommendationItemDto;
import com.geekbrains.spring.web.recommendation.converters.RecommendationConverter;
import com.geekbrains.spring.web.recommendation.repositories.RecommendationItemsRepository;
import com.geekbrains.spring.web.recommendation.services.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/rec")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;
    private final RecommendationConverter recommendationConverter;
    private final RecommendationItemsRepository recommendationItemsRepository;

    @GetMapping
    public List<RecommendationItemDto> getAllRecommendation(){
        LocalDateTime begin = LocalDateTime.now();
        LocalDateTime end = begin.minusMinutes(1L);
            return recommendationItemsRepository.findAllByDate(end, begin).stream()
                    .map(recommendationConverter::modelToDto).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createRecommendationByOrders(@RequestBody RecommendationDetailsDto recommendationDetailsDto) {
        if(recommendationDetailsDto.getFinishDate() != null) {
            recommendationService.createByOrders(recommendationDetailsDto);
        } else recommendationService.createByCarts(recommendationDetailsDto);
    }

}
