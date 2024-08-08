package com.book_keeping.boo.controller.response.entity;

import com.book_keeping.boo.Entity.Record;
import com.book_keeping.boo.commons.Bill_type;
import lombok.Data;

import java.io.Serializable;
@Data
public class EITableData implements Serializable {
    private Integer id;
    private String bill_type;
    private String kind;
    private Double amount;
    private String info;
    private String date;

    public EITableData(Record record) {
        this.id = record.getId();
        this.bill_type = record.getBillType() == 1 ? "收入" : "支出";
        this.kind = record.getKind();
        this.amount = record.getMoney();
        this.info = record.getInfo();
        this.date = record.getDate().toString().replace("T"," ");
    }

    public String toString() {
        return "EITableData(id=" + this.getId() + ", bill_type=" + this.getBill_type() + ", kind=" + this.getKind() + ", amount=" + this.getAmount() + ", info=" + this.getInfo() + ", date=" + this.getDate() + ")\n";
    }
}
