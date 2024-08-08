package com.book_keeping.boo.controller.requestEntity;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ExpenditureUpdateRequest {
    private Integer expenditureId;
    private String expenditureType;
    private Double expenditureAmount;
    private LocalDateTime expenditureDate;
    private String expenditureInfo;

    //针对修改支出的构造器
    public ExpenditureUpdateRequest(Integer expenditureId, String expenditureType, Double expenditureAmount, String expenditureDate, String expenditureInfo) {
        System.out.println("expenditureDate:" + expenditureDate);
        expenditureDate = expenditureDate.replace("T", " ");
        this.expenditureId = expenditureId;
        this.expenditureType = expenditureType;
        this.expenditureAmount = expenditureAmount;
        this.expenditureDate = LocalDateTime.parse(expenditureDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.expenditureInfo = expenditureInfo;
    }
}
