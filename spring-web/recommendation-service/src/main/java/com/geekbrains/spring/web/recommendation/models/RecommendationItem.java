package com.geekbrains.spring.web.recommendation.models;

import com.geekbrains.spring.web.api.dto.AnalyticItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recommendation_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_title")
    private String productTitle;


    @Column(name = "quantity")
    private Integer quantity;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public RecommendationItem(AnalyticItemDto analyticItemDto) {
        this.productId = analyticItemDto.getProductId();
        this.productTitle = analyticItemDto.getProductTitle();
        this.quantity = analyticItemDto.getQuantity();

    }

    public void changeQuantity(int delta) {
        this.quantity += delta;
    }
}
