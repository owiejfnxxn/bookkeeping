package com.book_keeping.boo.service;

import com.book_keeping.boo.Entity.Budget;
import com.book_keeping.boo.Entity.Category;
import com.book_keeping.boo.Entity.Repository.BudgetRepository;
import com.book_keeping.boo.Entity.Repository.CategoryRepository;
import com.book_keeping.boo.controller.response.BudgetInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RecordService recordService;

    // 获取预算信息
    public BudgetInfoResponse getBudgetInfo(Integer userId, String time_type, String time) {
        System.out.println("userId: " + userId);
        System.out.println("time_type: " + time_type);
        System.out.println("time: " + time);
        //获取预算信息
        List<Budget> budgets = budgetRepository.findByUserIdAndBudgetTime(userId, time);
        Budget totalBudget = handleTotalBudget(budgets,time_type,time,userId);
        System.out.println("budgets: " + budgets);
        System.out.println("totalBudget: " + totalBudget);
        if(totalBudget == null){
            // 无总预算
            return new BudgetInfoResponse(time, null, null, budgets);
        }
        BudgetInfoResponse budgetInfoResponse = new BudgetInfoResponse(time, totalBudget.getBudget_total().doubleValue(), totalBudget.getUsed_money().doubleValue(), budgets);
        System.out.println("budgetInfoResponse: " + budgetInfoResponse);
        return budgetInfoResponse;
    }
    // 处理总预算
    public Budget handleTotalBudget(List<Budget> budgets,String time_type,String time,Integer userId){
        if(budgets.size() == 0){
            // 无预算
            return null;
        }
        Budget totalBudget = null;
        for(Budget budget : budgets){
            if(budget.getBudget_kind().equals("总预算")){
                totalBudget = budget;
                break;
            }
        }
        if(totalBudget == null){
            return null;
        }
        budgets.remove(totalBudget);

        //获取总预算的已使用金额
        if(time_type.equals("month")){
            //获取当月总支出
            Double totalExpenditureMonth = recordService.getTotalExpenditureMonth(userId, time);
            if(totalExpenditureMonth == null){
                totalExpenditureMonth = 0.0;
            }
            totalBudget.setUsed_money(totalExpenditureMonth.floatValue());
            if(budgets.size() != 0){
                // 有类别预算
                handBudgetsMonth(budgets,time,userId);
            }
        }else{
            //获取当年总支出
            Double totalExpenditureYear = recordService.getTotalExpenditureYear(userId, time);
            if(totalExpenditureYear == null){
                totalExpenditureYear = 0.0;
            }
            totalBudget.setUsed_money(totalExpenditureYear.floatValue());
            if(budgets.size() != 0){
                // 有类别预算
                handBudgetsYear(budgets,time,userId);
            }
        }

        return totalBudget;
    }
    // 处理类别预算,月份型
    public void handBudgetsMonth(List<Budget> budgets,String time,Integer userId){
        budgets.forEach(budget -> {
            budget.setUsed_money(
                    recordService.getExpenditureTotalByMonthAndCategoryId(userId,time,budget.getCategory_id()).floatValue()
            );

        });
    }
    // 处理类别预算,年份型
    public void handBudgetsYear(List<Budget> budgets,String time,Integer userId){
        budgets.forEach(budget -> {
            budget.setUsed_money(
                    recordService.getExpenditureTotalByYearAndCategoryId(userId,time,budget.getCategory_id()).floatValue()
            );
        });
    }


    // 添加预算
    public Float addBudget(Integer userId, String time_kind, String budget_time, String budget_kind, Double budget_value) {
        //获取类别信息
        Category categoryInfo = categoryService.getIdByUserIdAndName(userId, budget_kind);
        //添加预算
        Budget budget = new Budget();

        budget.setBudget_kind(budget_kind);
        budget.setBudget_total(budget_value.floatValue());
        budget.setUsed_money(0.0f);
        budget.setUser_id(userId);
        budget.setBudget_time(budget_time);
        budget.setCategory_id(Integer.parseInt(categoryInfo.getCategoryId()));
        System.out.println("save budget: " + budget);
        //dao层添加预算
        budgetRepository.save(budget);
        //获取该预算的已使用金额
        if(time_kind.equals("month")) {
            //获取当月总支出
            Double totalExpenditureMonth = recordService.getExpenditureTotalByMonthAndCategoryId(userId, budget_time, Integer.parseInt(categoryInfo.getCategoryId()));
            if (totalExpenditureMonth == null) {
                totalExpenditureMonth = 0.0;
            }
            return totalExpenditureMonth.floatValue();
        }else{
            //获取当年总支出
            Double totalExpenditureYear = recordService.getExpenditureTotalByYearAndCategoryId(userId, budget_time, Integer.parseInt(categoryInfo.getCategoryId()));
            if (totalExpenditureYear == null) {
                totalExpenditureYear = 0.0;
            }
            return totalExpenditureYear.floatValue();
        }
    }

    // 修改预算
    public void updateBudget(Integer userId, String budget_time, String budgetName, Double budget_value) {
        // 根据类别名称获取类别id
        Category categoryInfo = categoryService.getIdByUserIdAndName(userId, budgetName);
        System.out.println("categoryInfo: " + categoryInfo);
        Integer updateNums = budgetRepository.updateBudgetTotalByUserIdAndBudgetTimeAndCategoryId(
                budget_value.floatValue(), userId, budget_time, Integer.parseInt(categoryInfo.getCategoryId())
        );
        if(updateNums == 0){
            addBudget(userId,"",budget_time,budgetName,budget_value);
        }
    }

    // 删除预算
    public void deleteBudget(Integer userId, String budget_time, String budgetName) {
        // 根据类别名称获取类别id
        Category categoryInfo = categoryService.getIdByUserIdAndName(userId, budgetName);
        System.out.println("categoryInfo: " + categoryInfo);
        budgetRepository.deleteByUserIdAndBudgetTimeAndCategoryId(userId, budget_time, Integer.parseInt(categoryInfo.getCategoryId()));
    }
}
