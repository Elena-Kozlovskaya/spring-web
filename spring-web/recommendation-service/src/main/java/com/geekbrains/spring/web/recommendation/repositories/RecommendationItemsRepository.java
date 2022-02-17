package com.geekbrains.spring.web.recommendation.repositories;

import com.geekbrains.spring.web.recommendation.models.RecommendationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RecommendationItemsRepository extends JpaRepository<RecommendationItem, Long> {
    @Query("select r from RecommendationItem r where r.createdAt between ?1 and ?2")
    List<RecommendationItem> findAllByDate(LocalDateTime createdAt, LocalDateTime finishedAt);
}
