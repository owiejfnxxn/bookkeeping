package com.book_keeping.boo.Entity;

import com.book_keeping.boo.controller.requestEntity.ExpenditurePutRequest;
import com.book_keeping.boo.controller.requestEntity.ExpenditureUpdateRequest;
import com.book_keeping.boo.controller.requestEntity.IncomePutRequest;
import com.book_keeping.boo.controller.requestEntity.IncomeUpdateRequest;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Data
@Table(name = "record")
public class Record implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "money")
    private Double money;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "record_create_time")
    private LocalDateTime recordCreateTime;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "record_update_time")
    private LocalDateTime recordUpdateTime;

    @Column(name = "info")
    private String info;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "bill_type")
    private Integer billType;

    @Column(name = "kind")
    private String kind;

    public Record(IncomePutRequest incomePutRequest, Integer userId) {
        this.money = incomePutRequest.getIncomeAmount();
        this.date = LocalDateTime.parse(incomePutRequest.getIncomeDate().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        this.info = incomePutRequest.getIncomeInfo();
        this.userId = userId;
        this.billType = 1;
        this.kind = incomePutRequest.getIncomeType();
        this.recordCreateTime = LocalDateTime.now();
        this.recordUpdateTime = null;

    }
    public Record(ExpenditurePutRequest expenditurePutRequest, Integer userId){
        this.money = expenditurePutRequest.getExpenditureAmount();
        this.date = LocalDateTime.parse(expenditurePutRequest.getExpenditureDate().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        this.info = expenditurePutRequest.getExpenditureInfo();
        this.userId = userId;
        this.billType = 0;
        this.kind = expenditurePutRequest.getExpenditureType();
        this.recordCreateTime = LocalDateTime.now();
        this.recordUpdateTime = null;
    }
    public Record(ExpenditureUpdateRequest expenditureUpdateRequest, Integer userId){
        this.id = expenditureUpdateRequest.getExpenditureId();
        this.money = expenditureUpdateRequest.getExpenditureAmount();
        this.date = LocalDateTime.parse(expenditureUpdateRequest.getExpenditureDate().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        this.info = expenditureUpdateRequest.getExpenditureInfo();
        this.userId = userId;
        this.billType = 0;
        this.kind = expenditureUpdateRequest.getExpenditureType();
        this.recordCreateTime = LocalDateTime.now();
        this.recordUpdateTime = null;
    }
    public Record(IncomeUpdateRequest incomeUpdateRequest, Integer userId){
        this.id = incomeUpdateRequest.getIncomeId();
        this.money = incomeUpdateRequest.getIncomeAmount();
        this.date = LocalDateTime.parse(incomeUpdateRequest.getIncomeDate().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        this.info = incomeUpdateRequest.getIncomeInfo();
        this.userId = userId;
        this.billType = 1;
        this.kind = incomeUpdateRequest.getIncomeType();
        this.recordCreateTime = LocalDateTime.now();
        this.recordUpdateTime = null;
    }
    public Record() {
    }
}
