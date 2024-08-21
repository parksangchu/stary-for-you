package com.stayforyou.auth.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private static final String USERNAME_KEY = "username";
    private static final String ROLE_KEY = "role";

    private final SecretKey accessKey;
    private final Long expiration;

    public JwtUtil(@Value("${spring.jwt.secret}") String accessKeyValue,
                   @Value("${spring.jwt.expiration}") Long expiration) {
        this.accessKey = Keys.hmacShaKeyFor(accessKeyValue.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }


    public String generateToken(String username, String role) {
        return Jwts.builder()
                .signWith(accessKey)
                .claim(USERNAME_KEY, username)
                .claim(ROLE_KEY, role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(accessKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get(USERNAME_KEY, String.class);
    }

    public String getRole(String token) {
        return Jwts.parser()
                .verifyWith(accessKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get(ROLE_KEY, String.class);
    }
}
