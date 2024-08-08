package com.book_keeping.boo.controller.response;

import com.book_keeping.boo.Entity.User;
import lombok.Data;

@Data
public class UserDetailsResponse {
    private Integer id;
    private String avatar;
    private String nickname;
    private String email;

    public UserDetailsResponse(User user) {
        this.id = user.getId();
        this.avatar = user.getAvatar();
        this.nickname = user.getUsername();
        this.email = user.getEmail();
    }
}
