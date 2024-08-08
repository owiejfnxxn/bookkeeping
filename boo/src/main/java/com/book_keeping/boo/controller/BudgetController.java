package com.book_keeping.boo.controller;

import com.book_keeping.boo.controller.response.BudgetInfoResponse;
import com.book_keeping.boo.service.BudgetService;
import com.book_keeping.boo.utils.JwtTools;
import com.book_keeping.boo.utils.ResponseResult;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class BudgetController {

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private BudgetService budgetService;

    /// 获取预算信息
    @GetMapping("/budget/info/{time_type}/{time}")
    public ResponseResult getBudgetInfo(@RequestHeader("accessToken") String token,
                                            @PathVariable("time_type") String time_type,
                                            @PathVariable("time") String time) {
        DefaultClaims dc = JwtTools.parseToken(token, secret);
        Integer userId = dc.get("id", Integer.class);
        return new ResponseResult(200,true,"请求成功",budgetService.getBudgetInfo(userId, time_type, time));
    }

    // 添加预算 返回该添加预算的已使用金额
    @PostMapping("/budget/add")
    public ResponseResult addBudget(@RequestHeader("accessToken") String token,
                                    @RequestParam("time_type") String time_kind,
                                    @RequestParam("time") String budget_time,
                                    @RequestParam("kind") String budget_kind,
                                    @RequestParam("value") Double budget_value) {
        DefaultClaims dc = JwtTools.parseToken(token, secret);
        Integer userId = dc.get("id", Integer.class);
        Float usedMoney = budgetService.addBudget(userId, time_kind, budget_time, budget_kind, budget_value);
        return new ResponseResult(200,true,"请求成功", usedMoney);
    }

    // 编辑预算
    @PutMapping("/budget/info/{time_type}/{time}")
    public ResponseResult editBudget(@RequestHeader("accessToken") String token,
                                     @PathVariable("time_type") String time_type,
                                     @PathVariable("time") String budget_time,
                                     @RequestParam("budgetName") String budgetName,
                                     @RequestParam("budgetValue") Double budgetValue) {
        DefaultClaims dc = JwtTools.parseToken(token, secret);
        Integer userId = dc.get("id", Integer.class);
        System.out.println("userId: " + userId);
        System.out.println("time_type: " + time_type);
        System.out.println("budget_time: " + budget_time);
        System.out.println("budgetName: " + budgetName);
        System.out.println("budgetValue: " + budgetValue);
        budgetService.updateBudget(userId, budget_time, budgetName, budgetValue);
        return ResponseResult.success();
    }

    // 删除预算
    @DeleteMapping("/budget/info/{time}/{budgetName}")
    public ResponseResult deleteBudget(@RequestHeader("accessToken") String token,
                                       @PathVariable("time") String budget_time,
                                       @PathVariable("budgetName") String budgetName) {
        DefaultClaims dc = JwtTools.parseToken(token, secret);
        Integer userId = dc.get("id", Integer.class);
        System.out.println("要删除的信息："+ userId + " " + budget_time + " " + budgetName);
        budgetService.deleteBudget(userId, budget_time, budgetName);
        return ResponseResult.success();
    }

}
