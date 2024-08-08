package com.book_keeping.boo.controller;

import com.book_keeping.boo.Entity.Record;
import com.book_keeping.boo.controller.requestEntity.ExpenditurePutRequest;
import com.book_keeping.boo.controller.requestEntity.ExpenditureUpdateRequest;
import com.book_keeping.boo.controller.requestEntity.IncomePutRequest;
import com.book_keeping.boo.service.RecordService;
import com.book_keeping.boo.utils.JwtTools;
import com.book_keeping.boo.utils.ResponseResult;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ExpenditureController {

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    RecordService recordService;

    @PostMapping("/expenditure")
    public ResponseResult expenditure(@RequestBody ExpenditurePutRequest expenditurePutRequest,
                                      @RequestHeader("accessToken") String token) {
        DefaultClaims defaultClaims = JwtTools.parseToken(token, secret);
        Integer userId = defaultClaims.get("id", Integer.class);
        Record record = recordService.addRecord(expenditurePutRequest, userId);
        return new ResponseResult(200, true, "请求成功", record.getId());
    }

    // 修改支出
    @PutMapping("/expenditure")
    public ResponseResult updateExpenditure(@RequestBody ExpenditureUpdateRequest expenditurePutRequest,
                                            @RequestHeader("accessToken") String token) {
        DefaultClaims defaultClaims = JwtTools.parseToken(token, secret);
        Integer userId = defaultClaims.get("id", Integer.class);
        Record record = recordService.updateRecord(expenditurePutRequest, userId);
        return new ResponseResult(200, true, "请求成功", record.getId());
    }

    // 按时间段获取支出
    @GetMapping("/expenditure/details/time-frame/{startTime}/{endTime}/{sortType}/{page}/{size}")
    public ResponseResult getExpenditureDetailsByTimeFrame(@PathVariable("startTime") String startTime,
                                                           @PathVariable("endTime") String endTime,
                                                           @PathVariable("sortType") String sortType,
                                                           @PathVariable("page") Integer page,
                                                           @PathVariable("size") Integer size,
                                                           @RequestHeader("accessToken") String token) {
        DefaultClaims defaultClaims = JwtTools.parseToken(token, secret);
        Integer userId = defaultClaims.get("id", Integer.class);
        return new ResponseResult(200, true, "请求成功", recordService.getExpenditureDetailsByTimeFrame(userId, sortType, startTime, endTime, page, size));
    }

    // 按月份获取支出
    @GetMapping("/expenditure/details/month/{month}/{sortType}/{page}/{size}")
    public ResponseResult getExpenditureDetailsByMonth(@PathVariable("month") String month,
                                                       @PathVariable("sortType") String sortType,
                                                       @PathVariable("page") Integer page,
                                                       @PathVariable("size") Integer size,
                                                       @RequestHeader("accessToken") String token) {
        DefaultClaims defaultClaims = JwtTools.parseToken(token, secret);
        Integer userId = defaultClaims.get("id", Integer.class);
        return new ResponseResult(200, true, "请求成功", recordService.getExpenditureDetailsByMonth(userId, sortType, month, page, size));
    }

    // 按年份获取支出
    @GetMapping("/expenditure/details/year/{year}/{sortType}/{page}/{size}")
    public ResponseResult getExpenditureDetailsByYear(@PathVariable("year") String year,
                                                      @PathVariable("sortType") String sortType,
                                                      @PathVariable("page") Integer page,
                                                      @PathVariable("size") Integer size,
                                                      @RequestHeader("accessToken") String token) {
        DefaultClaims defaultClaims = JwtTools.parseToken(token, secret);
        Integer userId = defaultClaims.get("id", Integer.class);
        return new ResponseResult(200, true, "请求成功", recordService.getExpenditureDetailsByYear(userId, year, sortType, page, size));
    }
}
