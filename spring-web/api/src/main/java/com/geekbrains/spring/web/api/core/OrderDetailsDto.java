package com.geekbrains.spring.web.api.core;

public class OrderDetailsDto {
    private String address;
    private String city;
    private String postalCode;
    private String phone;

    public OrderDetailsDto() {
    }

    public OrderDetailsDto(String address, String city, String postalCode, String phone) {
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
