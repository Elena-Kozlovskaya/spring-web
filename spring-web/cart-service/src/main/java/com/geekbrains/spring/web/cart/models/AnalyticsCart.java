package com.geekbrains.spring.web.cart.models;

import com.geekbrains.spring.web.api.core.ProductDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AnalyticsCart {
    private List<AnalyticCartItem> items;

    public AnalyticsCart() {
        this.items = new ArrayList<>();
    }

    public void add(ProductDto productDto) {
        if (add(productDto.getId())) {
            return;
        }
        items.add(new AnalyticCartItem(productDto));
    }

    public boolean add(Long productId) {
        for (AnalyticCartItem i : items) {
            if (i.getProductId().equals(productId)) {
                i.changeQuantity(1);
                return true;
            }
        }
        return false;
    }


    public void clear() {
        items.clear();
    }


    public void merge(AnalyticsCart another) {
        for (AnalyticCartItem anotherItem : another.items) {
            boolean merged = false;
            for (AnalyticCartItem myItem : items) {
                if (myItem.getProductId().equals(anotherItem.getProductId())) {
                    myItem.changeQuantity(anotherItem.getQuantity());
                    merged = true;
                    break;
                }
            }
            if (!merged) {
                items.add(anotherItem);
            }
        }
        another.clear();
    }
}
