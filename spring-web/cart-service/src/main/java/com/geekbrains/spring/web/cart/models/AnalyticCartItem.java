package com.geekbrains.spring.web.cart.models;

import com.geekbrains.spring.web.api.core.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticCartItem {
    private Long productId;
    private String productTitle;
    private int quantity;

    public AnalyticCartItem(ProductDto productDto) {
        this.productId = productDto.getId();
        this.productTitle = productDto.getTitle();
        this.quantity = 1;
    }

    public void changeQuantity(int delta) {
        this.quantity += delta;
    }
}
