package com.book_keeping.boo.Entity.Repository;

import com.book_keeping.boo.Entity.Budget;
import com.book_keeping.boo.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
    //根据用户id获取分类列表
    public List<Category> findAllByUserId(Integer userId);

    //根据用户id和分类名称获取分类id
    @Query(value = "select * from category where user_id = ?1 and category_name = ?2", nativeQuery = true)
    public Category findIdByUserIdAndName(Integer userId, String name);

    // 根据用户id和类别id删除类别
    @Query(value = "delete from category where category_id = ?1", nativeQuery = true)
    @Transactional
    @Modifying
    public void deleteByUserIdAndId(Integer categoryId);

    // 根据用户id和类别id获取类别名称
    @Query(value = "select * from category where user_id = ?1 and category_id = ?2", nativeQuery = true)
    public Category findNameByUserIdAndId(Integer userId, int parseInt);
}
