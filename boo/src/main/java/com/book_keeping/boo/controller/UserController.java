package com.book_keeping.boo.controller;

import com.book_keeping.boo.Entity.User;
import com.book_keeping.boo.controller.response.EITableDataResponse;
import com.book_keeping.boo.controller.response.LoginResponse;
import com.book_keeping.boo.controller.response.UserDetailsResponse;
import com.book_keeping.boo.service.UserService;
import com.book_keeping.boo.utils.JwtTools;
import com.book_keeping.boo.utils.ResponseResult;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${jwt.secret}")
    private String secret;

    // 登录
    @PostMapping("/user/login")
    public ResponseResult<LoginResponse> login(User user) {
        user = userService.login(user);
        // 从数据库中查询用户
        if (user != null) {
            // 获取用户token
            String userToken = JwtTools.getUserToken(user,secret);
            return new ResponseResult<>(200, true, "success", new LoginResponse(userToken));
        } else {
            // 用户名或密码错误
            return new ResponseResult(400, true, "账号或密码错误", null);
        }
    }

    // 用户获取自己的信息
    // 通过token获取用户信息
    @GetMapping("/user")
    public ResponseResult<EITableDataResponse> getUserInfo(@RequestHeader("accessToken") String token) {
        System.out.println("controller 中的 token: " + token);
        // 解析token
        DefaultClaims claims =  JwtTools.parseToken(token, secret);
        // 获取用户信息
        Integer userid = claims.get("id", Integer.class);
        String username = claims.get("username", String.class);
        String password = claims.get("password", String.class);
        System.out.println("userid: " + userid);
        System.out.println("username: " + username);
        System.out.println("password: " + password);
        // 从数据库获取用户信息
        User user = userService.getUserInfo(userid);
        return new ResponseResult(200, true, "success", new UserDetailsResponse(user));
    }
}
