package com.book_keeping.boo.Entity.Repository;

import com.book_keeping.boo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// 用户仓库
public interface UserRepository extends JpaRepository<User, Integer> {


    @Override
    // 查询所有用户
    List<User> findAll();

    // 根据用户名和密码查询用户
    User findByUsernameAndPassword(String username, String password);

}
