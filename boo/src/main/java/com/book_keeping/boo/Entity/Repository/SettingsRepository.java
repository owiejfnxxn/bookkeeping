package com.book_keeping.boo.Entity.Repository;

import com.book_keeping.boo.Entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Integer> {
    //修改设置
    @Query(value = "update settings set page_size = ?1, default_time_type = ?2 where userid = ?3", nativeQuery = true)
    @Modifying
    @Transactional
    Integer updateSettings(Integer pageSize, String defaultTimeType, Integer userId);

    // 根据用户id查询设置
    @Query(value = "select * from settings where userid = ?1", nativeQuery = true)
    Settings findByUserId(Integer userId);

    // 添加设置
    public Settings save(Settings settings);

    // 更新每页显示条数
    @Query(value = "update settings set page_size = ?1 where userid = ?2", nativeQuery = true)
    @Modifying
    @Transactional
    Integer updatePageNum(Integer pageSize, Integer userId);

    // 记录迁移
    @Query(value = "update record set category_id = ?1,kind=?2 where id = ?3", nativeQuery = true)
    @Modifying
    @Transactional
    Integer transform(Integer categoryId,String to_kind_name, Integer id);
}
