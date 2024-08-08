package com.book_keeping.boo.controller.requestEntity;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class IncomePutRequest {
    private Integer incomeId;
    private String incomeType;
    private Double incomeAmount;
    private LocalDateTime incomeDate;
    private String incomeInfo;
    //针对新增收入的构造器
    public IncomePutRequest(String incomeType, Double incomeAmount, String incomeDate, String incomeInfo) {
        System.out.println("incomeDate:" + incomeDate);
        incomeDate = incomeDate.replace("T", " ");
        this.incomeType = incomeType;
        this.incomeAmount = incomeAmount;
        this.incomeDate = LocalDateTime.parse(incomeDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.incomeInfo = incomeInfo;
    }
}
