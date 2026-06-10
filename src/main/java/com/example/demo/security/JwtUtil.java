package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // Secret key for signing JWT
    private final SecretKey secretKey =
            Keys.hmacShaKeyFor(
                    "mySuperSecretKeyForJwtAuthentication123456".getBytes()
            );

    private Claims extractClaims(String token) {

    return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
}       

    // Generate JWT Token
    public String generateToken(String email, String role) {

        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis() + 1000 * 60 * 60)
                ) // 1 hour
                .signWith(secretKey)
                .compact();
    }

    // Extract Email from Token
    public String extractEmail(String token) {

    return extractClaims(token)
            .getSubject();
    }
    

    // Extract Role from Token
    public String extractRole(String token) {

    return extractClaims(token).get("role", String.class);
}

    // Validate Token
    public boolean validateToken(String token) {

        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (Exception e) {

            return false;
        }
    }
}