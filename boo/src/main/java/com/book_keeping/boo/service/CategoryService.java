package com.book_keeping.boo.service;
import com.book_keeping.boo.Entity.Category;
import com.book_keeping.boo.Entity.Repository.CategoryRepository;
import com.book_keeping.boo.controller.response.CategoryListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    //获取分类列表
    public CategoryListResponse getCategoryList(Integer userId) {
        return new CategoryListResponse(categoryRepository.findAllByUserId(userId));
    }
    //通过用户id和类别名获取类别
    public Category getIdByUserIdAndName(Integer userId, String name) {
        return categoryRepository.findIdByUserIdAndName(userId, name);
    }

    // 根据用户id、类别名和类别类型添加类别
    public Category addCategory(Category category) {
        if(category.getCategoryType().equals("收入") || category.getCategoryType().equals("1")) {
            category.setCategoryType("1");
        }else if (category.getCategoryType().equals("支出") || category.getCategoryType().equals("0")) {
            category.setCategoryType("0");
        }else{
            return null;
        }

        return categoryRepository.save(category);
    }

    // 根据用户id和类别id删除类别
    public void deleteCategory(Category category) {
        System.out.println("要删除的数据 " + category);
        categoryRepository.deleteByUserIdAndId(Integer.parseInt(category.getCategoryId()));
        System.out.println("删除成功");
    }

    public Category getNameByUserIdAndId(Integer userId, int parseInt) {
        return categoryRepository.findNameByUserIdAndId(userId, parseInt);
    }
}
