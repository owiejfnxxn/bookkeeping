package com.book_keeping.boo.controller.requestEntity;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class IncomeUpdateRequest {
    private Integer incomeId;
    private String incomeType;
    private Double incomeAmount;
    private LocalDateTime incomeDate;
    private String incomeInfo;

    public IncomeUpdateRequest(String incomeId, String incomeType, String incomeAmount, String incomeDate, String incomeInfo) {
        System.out.println("incomeDate:" + incomeDate);
        incomeDate = incomeDate.replace("T", " ");
        this.incomeId = Integer.parseInt(incomeId);
        this.incomeType = incomeType;
        this.incomeAmount = Double.parseDouble(incomeAmount);
        this.incomeDate = LocalDateTime.parse(incomeDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.incomeInfo = incomeInfo;
    }
}
