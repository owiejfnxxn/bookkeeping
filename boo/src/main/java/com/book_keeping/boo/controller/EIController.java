package com.book_keeping.boo.controller;

import com.book_keeping.boo.controller.requestEntity.EITableDataRequest;
import com.book_keeping.boo.controller.response.EITableDataResponse;
import com.book_keeping.boo.service.RecordService;
import com.book_keeping.boo.utils.JwtTools;
import com.book_keeping.boo.utils.ResponseResult;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class EIController {

    @Autowired
    private RecordService recordService;

    @Value("${jwt.secret}")
    private String secret;

    @GetMapping("/details")
    public ResponseResult getDetails(EITableDataRequest request,
                                     @RequestHeader("accessToken") String accessToken) {
        DefaultClaims defaultClaims = JwtTools.parseToken(accessToken, secret);
        EITableDataResponse eiTableData = recordService.getEITableData((Integer) defaultClaims.get("id"), request.getStartTime().toString(), request.getEndTime().toString(), request.getPage(),request.getSize());
        return new ResponseResult(200,true,"请求成功",eiTableData);
    }

    @GetMapping("/details/month/{month}/{page}/{size}")
    public ResponseResult getDetailsByMonth(@PathVariable("month") String month,
                                            @PathVariable("page") Integer page,
                                            @PathVariable("size") Integer size,
                                            @RequestHeader("accessToken") String accessToken) {
        DefaultClaims defaultClaims = JwtTools.parseToken(accessToken, secret);
        EITableDataResponse eiTableData = recordService.getAllRecordByUserIdAndMonth((Integer) defaultClaims.get("id"), month, page, size);
        return new ResponseResult(200,true,"请求成功",eiTableData);
    }

    @GetMapping("/details/year/{year}/{page}/{size}")
    public ResponseResult getDetailsByYear(@PathVariable("year") String year,
                                           @PathVariable("page") Integer page,
                                           @PathVariable("size") Integer size,
                                           @RequestHeader("accessToken") String accessToken) {
        DefaultClaims defaultClaims = JwtTools.parseToken(accessToken, secret);
        EITableDataResponse eiTableData = recordService.getAllRecordByUserIdAndYear((Integer) defaultClaims.get("id"), year, page, size);
        return new ResponseResult(200,true,"请求成功",eiTableData);
    }

    @GetMapping("/details/transform/{categoryId}")
    public ResponseResult getDetailsByCategory(@PathVariable("categoryId") int categoryId,
                                               @RequestHeader("accessToken") String accessToken) {
        DefaultClaims defaultClaims = JwtTools.parseToken(accessToken, secret);
        EITableDataResponse eiTableData = recordService.getAllRecordByUserIdAndCategory((Integer) defaultClaims.get("id"), categoryId);
        return new ResponseResult(200,true,"请求成功",eiTableData);
    }
}
