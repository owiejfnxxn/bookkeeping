package com.book_keeping.boo.controller.response;

import com.book_keeping.boo.Entity.Record;
import com.book_keeping.boo.controller.response.entity.EITableData;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
// 收入支出的表格数据
public class EITableDataResponse implements Serializable {

    List<EITableData> list = new ArrayList<>();
    Double incomeTotal = 0.0;
    Double expenditureTotal = 0.0;
    Integer totalPage = 0;
    public EITableDataResponse(List<Record> records,Double incomeTotal,Double expenditureTotal, Integer totalPage) {
        for (Record record : records) {
            list.add(new EITableData(record));
        }
        this.incomeTotal = incomeTotal;
        this.expenditureTotal = expenditureTotal;
        // 如果总收入和总支出为null,默认为0
        if (this.incomeTotal == null) {
            this.incomeTotal = 0.0;
        }
        if (this.expenditureTotal == null) {
            this.expenditureTotal = 0.0;
        }
        this.totalPage = totalPage;
    }

}
