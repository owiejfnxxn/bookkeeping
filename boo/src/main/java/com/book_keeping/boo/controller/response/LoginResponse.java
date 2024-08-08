package com.book_keeping.boo.controller.response;

import lombok.Data;

@Data
public class LoginResponse {
    public LoginResponse(String token) {
        this.token = token;
    }
    private String token;
}
