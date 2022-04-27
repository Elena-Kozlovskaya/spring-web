package com.geekbrains.spring.web.core.entities;

import org.springframework.context.ApplicationEvent;

public class OrderEvent extends ApplicationEvent {
    private String message;

    public String getMessage() {
        return message;
    }

    public OrderEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
}
