package com.book_keeping.boo.controller;

import com.book_keeping.boo.Entity.Record;
import com.book_keeping.boo.controller.requestEntity.IncomePutRequest;
import com.book_keeping.boo.controller.requestEntity.IncomeUpdateRequest;
import com.book_keeping.boo.service.RecordService;
import com.book_keeping.boo.utils.JwtTools;
import com.book_keeping.boo.utils.ResponseResult;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class IncomeController {

    @Autowired
    RecordService recordService;

    @Value("${jwt.secret}")
    private String secret;

    @PostMapping("/income")
    public ResponseResult income(@RequestBody IncomePutRequest incomePutRequest,
                                 @RequestHeader("accessToken") String token) {
        DefaultClaims defaultClaims = JwtTools.parseToken(token, secret);
        Integer userId = defaultClaims.get("id", Integer.class);
        Record record = recordService.addRecord(incomePutRequest, userId);
        return new ResponseResult(200,true,"请求成功",record.getId());
    }

    @PutMapping("/income")
    public ResponseResult updateIncome(@RequestBody IncomeUpdateRequest incomeUpdateRequest,
                                       @RequestHeader("accessToken") String token) {
        System.out.println(incomeUpdateRequest);
        DefaultClaims defaultClaims = JwtTools.parseToken(token, secret);
        Integer userId = defaultClaims.get("id", Integer.class);
        Record record = recordService.updateRecord(incomeUpdateRequest, userId);
        return new ResponseResult(200,true,"请求成功",record.getId());
    }

    @DeleteMapping("/income")
    public ResponseResult deleteIncome(@RequestParam("id") Integer id) {
        recordService.deleteRecord(id);
        return new ResponseResult(200,true,"请求成功",id);
    }

    // 根据时间段获取收入详情
    @GetMapping("/income/details/time-frame/{startTime}/{endTime}/{sortType}/{page}/{size}")
    public ResponseResult getIncomeDetailsByTimeFrame(@PathVariable("startTime") String startTime,
                                                      @PathVariable("endTime") String endTime,
                                                      @PathVariable("sortType") String sortType,
                                                      @PathVariable("page") Integer page,
                                                      @PathVariable("size") Integer size,
                                                      @RequestHeader("accessToken") String token) {
        DefaultClaims defaultClaims = JwtTools.parseToken(token, secret);
        Integer userId = defaultClaims.get("id", Integer.class);
        return new ResponseResult(200,true,"请求成功",recordService.getIncomeDetailsByTimeFrame(userId ,sortType, startTime, endTime, page, size));
    }

    // 根据月份获取收入详情
    @GetMapping("/income/details/month/{month}/{sortBy}/{page}/{size}")
    public ResponseResult getIncomeDetailsByMonth(@PathVariable("month") String month,
                                        @PathVariable("sortBy") String sortBy,
                                        @PathVariable("page") Integer page,
                                        @PathVariable("size") Integer size,
                                        @RequestHeader("accessToken") String token) {
        DefaultClaims defaultClaims = JwtTools.parseToken(token, secret);
        Integer userId = defaultClaims.get("id", Integer.class);
        return new ResponseResult(200,true,"请求成功",recordService.getIncomeDetailsByMonth(userId, month, sortBy, page, size));
    }

    // 根据年份获取收入详情
    @GetMapping("/income/details/year/{year}/{sortBy}/{page}/{size}")
    public ResponseResult getIncomeDetailsByYear(@PathVariable("year") String year,
                                       @PathVariable("sortBy") String sortBy,
                                       @PathVariable("page") Integer page,
                                       @PathVariable("size") Integer size,
                                       @RequestHeader("accessToken") String token) {
        DefaultClaims defaultClaims = JwtTools.parseToken(token, secret);
        Integer userId = defaultClaims.get("id", Integer.class);
        return new ResponseResult(200,true,"请求成功",recordService.getIncomeDetailsByYear(userId, year, sortBy, page, size));
    }
}
