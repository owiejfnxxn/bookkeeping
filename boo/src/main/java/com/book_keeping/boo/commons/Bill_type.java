package com.book_keeping.boo.commons;

public enum Bill_type {
     收入(1),
    支出(0);
    private Integer type;

    Bill_type(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
