package com.geekbrains.spring.web.api.recommendation;

public class RecommendationItemDto {
    private Long productId;
    private String productTitle;
    private int quantity;

    public RecommendationItemDto(Long productId, String productTitle, int quantity) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.quantity = quantity;
    }

    public RecommendationItemDto() {
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
