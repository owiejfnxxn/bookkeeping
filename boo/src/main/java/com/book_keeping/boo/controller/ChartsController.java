package com.book_keeping.boo.controller;

import com.book_keeping.boo.service.ChartsService;
import com.book_keeping.boo.utils.JwtTools;
import com.book_keeping.boo.utils.ResponseResult;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ChartsController {
    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private ChartsService chartsService;

    // 获取图表数据
    @GetMapping("/chart/{chart_type}/{time_type}/{start_time}/{end_time}")
    public ResponseResult getChartsData(@PathVariable("chart_type") String chart_type,
                                        @PathVariable("time_type") String time_type,
                                        @PathVariable("start_time") String start_time,
                                        @PathVariable("end_time") String end_time,
                                        @RequestHeader("accessToken") String token) {
        DefaultClaims dc = JwtTools.parseToken(token, secret);
        Integer userId = Integer.parseInt(dc.get("id").toString());
        return ResponseResult.success(chartsService.getChartsData(chart_type,time_type,start_time, end_time, userId));
    }
}
