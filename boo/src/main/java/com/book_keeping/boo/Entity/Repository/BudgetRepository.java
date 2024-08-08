package com.book_keeping.boo.Entity.Repository;

import com.book_keeping.boo.Entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {
    // 添加预算
    public Budget save(Budget budget);

    // 获取预算信息
    @Query(value = "select * from budget where user_id = ?1 and budget_time = ?2", nativeQuery = true)
    public List<Budget> findByUserIdAndBudgetTime(Integer userId, String time);

    // 按用户id、预算时间和总类id修改预算值
    @Modifying
    @Transactional
    @Query(value = "update budget set budget_total = ?1 where user_id = ?2 and budget_time = ?3 and category_id = ?4", nativeQuery = true)
    public Integer updateBudgetTotalByUserIdAndBudgetTimeAndCategoryId(Float budgetTotal, Integer userId, String budgetTime, Integer categoryId);

    // 按用户id、预算时间和类别id删除预算
    @Modifying
    @Transactional
    @Query(value = "delete from budget where user_id = ?1 and budget_time = ?2 and category_id = ?3", nativeQuery = true)
    public Integer deleteByUserIdAndBudgetTimeAndCategoryId(Integer userId, String budgetTime, Integer categoryId);
}
