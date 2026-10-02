package com.ecommerce.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());

    }
    public Claims extractClaimsJWT(String token) {
        return Jwts.parser().verifyWith(secretKey)
                .build().parseSignedClaims(token).getPayload();
    }
    public boolean validateToken(String token) {
        try {
            Claims claims = extractClaimsJWT(token);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
    public String getUserId(String token) {
        return extractClaimsJWT(token).getSubject();
    }
    public String getRole(String token){
        return extractClaimsJWT(token).get("role",String.class);
    }
}
