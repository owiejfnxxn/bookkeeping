package com.book_keeping.boo.controller;

import com.book_keeping.boo.Entity.Category;
import com.book_keeping.boo.controller.response.CategoryListResponse;
import com.book_keeping.boo.service.CategoryService;
import com.book_keeping.boo.utils.JwtTools;
import com.book_keeping.boo.utils.ResponseResult;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class CategoryController {

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private CategoryService categoryService;

    //根据id获取类别列表
    @GetMapping("/category")
    public ResponseResult category(@RequestHeader("accessToken") String token) {
        DefaultClaims defaultClaims = JwtTools.parseToken(token, secret);
        CategoryListResponse userId = categoryService.getCategoryList(defaultClaims.get("id", Integer.class));
        System.out.println(" category userId " + userId);
        return ResponseResult.success(categoryService.getCategoryList(defaultClaims.get("id", Integer.class)));
    }

    // 添加类别
    @PostMapping("/category")
    public ResponseResult addCategory(@RequestBody Category category, @RequestHeader("accessToken") String token) {
        DefaultClaims defaultClaims = JwtTools.parseToken(token, secret);
        category.setUserId(defaultClaims.get("id", Integer.class));
        System.out.println("addCategory " + category);
        return ResponseResult.success(categoryService.addCategory(category));
    }

    // 删除类别
    @DeleteMapping("/category")
    public ResponseResult deleteCategory(@RequestBody Category category, @RequestHeader("accessToken") String token) {
        DefaultClaims defaultClaims = JwtTools.parseToken(token, secret);
        category.setUserId(defaultClaims.get("id", Integer.class));
        categoryService.deleteCategory(category);
        return ResponseResult.success();
    }
}
