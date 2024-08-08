package com.book_keeping.boo.config;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTConfig {
    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public JwtParser jwtParser(){
        return Jwts.parser().setSigningKey(secret);
    }

    @Bean
    public JwtBuilder jwtBuilder(){
        return Jwts.builder().signWith(SignatureAlgorithm.HS256,secret);
    }
}
