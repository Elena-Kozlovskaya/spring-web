package com.geekbrains.spring.web.api.recommendation;

public class RecommendationDetailsDto {
    private String startDate;
    private String finishDate;

    public RecommendationDetailsDto() {
    }

    public RecommendationDetailsDto(String startDate, String finishDate) {
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }
}
