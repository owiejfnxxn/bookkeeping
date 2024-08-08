package com.book_keeping.boo.controller.requestEntity;

import lombok.Data;

@Data
public class TransformDataRequest {
    private Integer id;
    private String bill_type;
    private String amount;
    private String kind;
    private String date;
    private String info;

public TransformDataRequest(Integer id, String bill_type, String kind, String amount, String info, String date) {
        this.id = id;
        this.bill_type = bill_type;
        this.amount = amount;
        this.kind = kind;
        this.date = date;
        this.info = info;
    }
}
