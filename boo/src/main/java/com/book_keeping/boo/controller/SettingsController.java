package com.book_keeping.boo.controller;

import com.book_keeping.boo.Entity.Record;
import com.book_keeping.boo.Entity.Settings;
import com.book_keeping.boo.controller.requestEntity.TransformDataRequest;
import com.book_keeping.boo.service.SettingsService;
import com.book_keeping.boo.utils.JwtTools;
import com.book_keeping.boo.utils.ResponseResult;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class SettingsController {
    @Autowired
    SettingsService settingsService;

    @Value("${jwt.secret}")
    private String secret;

    @PutMapping("/settings")
    public ResponseResult settings(@RequestBody Settings settings, @RequestHeader("accessToken") String token) {
        DefaultClaims defaultClaims = JwtTools.parseToken(token, secret);
        Integer userId = defaultClaims.get("id", Integer.class);
        settings.setUserid(userId);
        return ResponseResult.success(settingsService.updateSettings(settings));
    }

    @GetMapping("/settings/default")
    public ResponseResult settingsDefault(@RequestHeader("accessToken") String token) {
        DefaultClaims defaultClaims = JwtTools.parseToken(token, secret);
        Integer userId = defaultClaims.get("id", Integer.class);
        System.out.println("userId"+userId);
        return ResponseResult.success(settingsService.getDefaultSettings(userId));
    }

    @GetMapping("/settings")
    public ResponseResult getSettings(@RequestHeader("accessToken") String token) {
        DefaultClaims defaultClaims = JwtTools.parseToken(token, secret);
        Integer userId = defaultClaims.get("id", Integer.class);
        return ResponseResult.success(settingsService.getSettings(userId));
    }

    // 修改每页显示条数
    @PutMapping("/settings/pageNum/{pageNum}")
    public ResponseResult updatePageNum(@PathVariable("pageNum") int pageNum, @RequestHeader("accessToken") String token) {
        DefaultClaims defaultClaims = JwtTools.parseToken(token, secret);
        Integer userId = defaultClaims.get("id", Integer.class);
        return ResponseResult.success(settingsService.updatePageNum(pageNum, userId));
    }

    // 修改默认日期类型
    @PutMapping("/settings/defaultTimeType/{defaultTimeType}")
    public ResponseResult updateDefaultTimeType(@PathVariable("defaultTimeType") String defaultTimeType, @RequestHeader("accessToken") String token) {
        DefaultClaims defaultClaims = JwtTools.parseToken(token, secret);
        Integer userId = defaultClaims.get("id", Integer.class);
        System.out.println("defaultTimeType: " + defaultTimeType);
        return ResponseResult.success(settingsService.updateDefaultTimeType(defaultTimeType, userId));
    }

    // 记录迁移
    @PostMapping("/settings/transform/{from}/{to}")
    public ResponseResult transform(@RequestHeader("accessToken") String token,
                                    @RequestBody ArrayList<TransformDataRequest> list,
                                    @PathVariable("from") String from,
                                    @PathVariable("to") String to) {
        DefaultClaims defaultClaims = JwtTools.parseToken(token, secret);
        Integer userId = defaultClaims.get("id", Integer.class);
        System.out.println("data: " + list.toString());
        return ResponseResult.success(settingsService.transform(list, from, to, userId));
    }
}
