package com.book_keeping.boo.controller.requestEntity;

import lombok.Data;

import java.sql.Date;

@Data
public class EITableDataRequest {
    Date startTime;
    Date endTime;
    Integer page;
    Integer size;

    public EITableDataRequest(Date start_time, Date end_time, Integer page, Integer size) {
        this.startTime = start_time;
        this.endTime = end_time;
        this.page = page;
        this.size = size;
    }
}
