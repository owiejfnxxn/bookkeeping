package com.book_keeping.boo.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseResult <T>{
    private int code;
    private Boolean success;
    private String msg;
    private T data;

    public static ResponseResult success() {
        return new ResponseResult(200, true, "success", null);
    }

    public static ResponseResult success(Object data) {
        return new ResponseResult(200, true, "success", data);
    }

    public static ResponseResult fail() {
        return new ResponseResult(500, false, "fail", null);
    }

    public static ResponseResult fail(String msg) {
        return new ResponseResult(500, false, msg, null);
    }


}
