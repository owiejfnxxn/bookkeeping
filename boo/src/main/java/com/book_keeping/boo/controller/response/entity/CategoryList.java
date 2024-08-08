package com.book_keeping.boo.controller.response.entity;

import com.book_keeping.boo.Entity.Category;
import lombok.Data;

@Data
public class CategoryList {
    private String category_id;
    private String category_name;
    private String category_type;

    public CategoryList(Category category) {
        this.category_name = category.getCategoryName();
        this.category_type = category.getCategoryType().equals("0") ? "支出" : category.getCategoryType().equals("1") ? "收入" : "其他";
        this.category_id = category.getCategoryId();
    }
}
