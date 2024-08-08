package com.book_keeping.boo.commons;

import org.springframework.beans.factory.annotation.Value;

public enum JwtSecret {
    JWT_SECRET("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VybmFtZSIsImlhdCI6MTYxOTU1ODQ5NSwiZXhwIjoxNjE5NTYyMDk1fQ.SkrpzVb03DPq3_QCk1IYIuJ5X5V7IKuwxl2rVrCz-5k\n");
    private String data;
    JwtSecret(String data) {
        this.data = data;
    }
}
