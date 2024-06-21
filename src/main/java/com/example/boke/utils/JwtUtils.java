package com.example.boke.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Slf4j
public class JwtUtils {
    private static final Long EXPIRATION_TIME = 43200000L; // 12 hours
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);


    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public static Claims extractAllClaims(String token) {
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            log.error("解析JWT时发生异常", e);
            throw e;
        }

    }


    public static Long extractUserId(String token) {
        try {
            Claims claims = extractAllClaims(token);
            // 从Claims中取出ID
            return Long.parseLong(claims.get("userId").toString());  // 确保在生成token时使用的键与此处一致
        }catch (Exception e){
            log.error("解析JWT时发生异常", e);
            throw e;
        }

    }
}
