package com.book_keeping.boo.service;

import com.book_keeping.boo.Entity.Repository.UserRepository;
import com.book_keeping.boo.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    // 用户仓库
    private UserRepository userRepository;
    @Autowired
    private RecordService recordService;

    public User login(User user) {
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    public User getUserInfo(Integer userId) {
        // 通过用户id获取用户详细信息
        User user = userRepository.findById(userId).get();
        return user;
    }


}
