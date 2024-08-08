package com.book_keeping.boo.Entity.Repository;

import com.book_keeping.boo.Entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Integer> {

    // 查询所有记录
    @Override
    List<Record> findAll();

    // 通过用户id查询所有记录
    List<Record> findAllByUserId(Integer userId);

    // 通过用户id查询在时间区间中的总记录数
    @Query(value = "select count(*) from record where user_id = ?1 and date between ?2 and ?3", nativeQuery = true)
    Integer     getTotalRecordByUserId(Integer userId , LocalDateTime startTime, LocalDateTime endTime);

    // 通过用户id和时间区间查询所有记录
    List<Record> findAllByUserIdAndDateBetween(Integer userId, LocalDateTime startTime, LocalDateTime endTime);

    // 通过用户id和时间区间分页查询使用记录,按时间由近到远
    @Query(value = "select * from record where user_id = ?1 and date between ?2 and ?3 order by date desc limit ?4,?5", nativeQuery = true)
    List<Record> findAllByUserIdAndDateBetween(Integer userId, LocalDateTime startTime, LocalDateTime endTime, Integer start, Integer end);

    // 通过用户id和时间区间分页查询收入/支出记录,按时间由近到远
    @Query(value = "select * from record where user_id = ?1 and bill_type = ?2 and date between ?3 and ?4 order by date desc limit ?5,?6", nativeQuery = true)
    List<Record> findAllByUserIdAndDateBetweenAndBillTypeOrderByDateDesc(Integer userId, Integer bill_type, LocalDateTime startTime, LocalDateTime endTime,Integer start, Integer end);

    // 通过用户id和时间区间分页查询收入/支出记录,按金额由大到小
    @Query(value = "select * from record where user_id = ?1 and bill_type = ?2 and date between ?3 and ?4 order by money desc limit ?5,?6", nativeQuery = true)
    List<Record> findAllByUserIdAndDateBetweenAndBillTypeOrderByMoneyDesc(Integer userId, Integer bill_type, LocalDateTime startTime, LocalDateTime endTime,Integer start, Integer end);

    // 通过用户id和时间区间查询总收入/支出,按时间由近到远或按金额由大到小的总条数
    @Query(value = "select count(*) from record where user_id = ?1 and bill_type= ?2 and date between ?3 and ?4", nativeQuery = true)
    Integer getTotalRecordByUserIdAndDateBetweenAndBillType(Integer userId, Integer bill_type, LocalDateTime startTime, LocalDateTime endTime);

    // 通过用户id和时间区间查询所有收入/支出记录
//    @Query(value = "select * from record where user_id = ?1 and date between ?2 and ?3 and bill_type = ?4", nativeQuery = true)
//    List<Record> findAllByUserIdAndDateBetweenAndBillType(Integer userId, LocalDateTime startTime, LocalDateTime endTime, Integer type);

    // 通过用户id和时间区间查询总收入/支出
    @Query(value = "select sum(money) from record where user_id = ?1 and date between ?2 and ?3 and bill_type = ?4", nativeQuery = true)
    Double findTotalMoneyByUserIdAndDateBetweenAndBillType(Integer userId, LocalDateTime startTime, LocalDateTime endTime, Integer type);

    // 添加记录
    @Override
    Record save(Record record);

    // 通过id删除记录
    @Override
    void deleteById(Integer id);

    // 通过id获取总记录数
    @Query(value = "select count(*) from record where user_id = ?1", nativeQuery = true)
    Integer getTotalRecordByUserId(Integer userId);

    // 通过类别id获取和时间区间该类别的总支出/收入
    @Query(value = "select sum(money) from record where user_id = ?1 and date between ?2 and ?3 and category_id = ?4", nativeQuery = true)
    Double getTotalMoneyByUserIdAndDateBetweenAndCategoryId(Integer userId, LocalDateTime startTime, LocalDateTime endTime, Integer categoryId);

    // 通过类别id和用户id获取全部记录
    @Query(value = "select * from record where user_id = ?1 and category_id = ?2", nativeQuery = true)
    List<Record> findAllByUserIdAndCategoryId(Integer userId, Integer categoryId);
}
