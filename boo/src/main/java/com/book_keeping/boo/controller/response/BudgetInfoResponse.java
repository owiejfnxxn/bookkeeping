package com.book_keeping.boo.controller.response;

import com.book_keeping.boo.Entity.Budget;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class BudgetInfoResponse implements Serializable {

    @Data
    private class TableData{
        private String name;
        private Double totalBudget;
        private Double usedBudget;

        public TableData(Budget budget) {
            this.name = budget.getBudget_kind();
            this.totalBudget = budget.getBudget_total().doubleValue();
            this.usedBudget = budget.getUsed_money().doubleValue();
        }
    }

    private String title;
    private Double totalBudget;
    private Double usedBudget;
    private List<TableData> tableData = new ArrayList<>();

    // 全参构造函数
    public BudgetInfoResponse(String title, Double totalBudget, Double usedBudget, List<Budget> budgets) {
        this.title = title+" 的总预算";
        this.totalBudget = totalBudget;
        this.usedBudget = usedBudget;
        budgets.forEach(budget -> {
            tableData.add(new TableData(budget));
        });
    }
}
