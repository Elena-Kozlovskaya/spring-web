package com.geekbrains.spring.web.api.core;

public class ProfileDto {
    private String username;


    public ProfileDto(String username) {
        this.username = username;
    }
    public ProfileDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
