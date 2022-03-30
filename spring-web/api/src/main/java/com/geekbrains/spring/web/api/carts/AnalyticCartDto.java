package com.geekbrains.spring.web.api.carts;

import com.geekbrains.spring.web.api.dto.AnalyticItemDto;

import java.util.List;

public class AnalyticCartDto {
    private List<AnalyticItemDto> items;

    public AnalyticCartDto() {
    }

    public AnalyticCartDto(List<AnalyticItemDto> items) {
        this.items = items;
    }

    public List<AnalyticItemDto> getItems() {
        return items;
    }

    public void setItems(List<AnalyticItemDto> items) {
        this.items = items;
    }
}
