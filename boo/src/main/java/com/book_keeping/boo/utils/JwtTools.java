package com.book_keeping.boo.utils;

import com.book_keeping.boo.Entity.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;

import java.util.Date;

public class JwtTools {

    public static String getUserToken(User user, String secret) {
        JwtBuilder builder = Jwts.builder().setSubject("user")
                .claim("id", user.getId())
                .claim("username", user.getUsername())
                .claim("password", user.getPassword())
                .setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS256, secret);
        return builder.compact();
    }

    public static DefaultClaims parseToken(String token, String secret) {
        return (DefaultClaims) Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
