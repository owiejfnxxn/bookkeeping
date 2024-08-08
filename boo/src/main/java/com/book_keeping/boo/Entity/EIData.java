package com.book_keeping.boo.Entity;

import lombok.Data;

@Data
public class EIData {
    private Integer id;
    private String bill_type;
    private String money;
    private String kind;
    private String date;

    public EIData(Integer id, String bill_type, String money, String kind, String date) {
        this.id = id;
        this.bill_type = bill_type;
        this.money = money;
        this.kind = kind;
        this.date = date;
    }
}
