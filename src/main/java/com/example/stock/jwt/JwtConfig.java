package com.example.stock.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secretKeyPlain;

    @Bean
    public SecretKey secretKey() {
        byte[] keyBytes = secretKeyPlain.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public long getExpirationTime() {
        return 1000 * 60 * 30 ;    }
}
