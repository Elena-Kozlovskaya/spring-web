package com.geekbrains.spring.web.api.dto;

public class AnalyticItemDto {
    private Long productId;
    private String productTitle;
    private int quantity;

    public AnalyticItemDto(Long productId, String productTitle, int quantity) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.quantity = quantity;
    }

    public AnalyticItemDto() {
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
