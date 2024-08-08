package com.book_keeping.boo.controller.response;

import com.book_keeping.boo.Entity.Category;
import com.book_keeping.boo.controller.response.entity.CategoryList;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryListResponse {
    List<CategoryList> list = new ArrayList<>();

    public CategoryListResponse(List<Category> list) {
for (Category category : list) {
            this.list.add(new CategoryList(category));
        }
    }
}
