package com.ecommerce.user.util;

import com.ecommerce.user.dto.UserCreatedRequestDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final SecretKey key;
    private final long jwtExpirationInMs;


    public JwtUtil(@Value("${app.jwt.secret}") String secret ,@Value("${app.jwt.expiration-ms}") long jwtExpirationInMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.jwtExpirationInMs = jwtExpirationInMs;
    }
    public String generateToken(long userId,String email,String role) {
        long nowMillis = System.currentTimeMillis();

        return Jwts.builder().subject(String.valueOf(userId))
                .claim("email",email)
                .claim("roles",role)
                .issuedAt(new Date(nowMillis))
                .expiration(new Date(jwtExpirationInMs))
                .signWith(key)
                .compact();
    }

    public Jws<Claims> validateToken(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
    }

   public Long getUserIdFromToken(String token){
       Claims claims = validateToken(token).getPayload();
       return Long.valueOf(claims.getSubject());
        }

}
