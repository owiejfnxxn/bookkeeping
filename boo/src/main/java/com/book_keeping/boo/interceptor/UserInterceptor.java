package com.book_keeping.boo.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(" 拦截器中的 accessToken "+request.getHeader("accessToken"));
        String accessToken = request.getHeader("accessToken");
        if(accessToken == null){
            //重定向到登录页面
        }else{
            //放行
            return true;
        }
        return false;
    }
}
