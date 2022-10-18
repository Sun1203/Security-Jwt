package com.hanhea.jwtlike.jwt.jwt_utils;

import com.hanhea.jwtlike.jwt.custom_Filter.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTUtil {
    private final UserDetailsServiceImpl userDetailsService;

    private final RedisTemplate<String, String> redisTemplate;

    private static final Long ACCESS_TIME = 10*1000*60L; //3분
    public static final String ACCESS_TOKEN = "Access_Token";
    private static final Long REFRESH_TIME = 20*1000*60L; //7분

    @Value("${jwt.secret}")
    private String secretKey;

    private Key key;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;


    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createAllToken(String nickname){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(nickname, createToken(nickname, "RF"), REFRESH_TIME, TimeUnit.MILLISECONDS);
        return createToken(nickname, "Access");
    }

    //토큰 생성
    public String createToken(String nickname, String type){
        Date date = new Date();
        long time = type.equals("Access") ? ACCESS_TIME : REFRESH_TIME;
        return Jwts.builder()
                .setSubject(nickname)
                .setExpiration(new Date(date.getTime()+time))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    //AC토큰 검증
    public Boolean tokenValidation(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            log.error(ex.getMessage());
            System.out.println("토큰기간 만료");
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            System.out.println("그외 오류");
            return false;
        }
    }


    //RF토큰 검증
    public Boolean rftokenvalid(String nickname) {
        try {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            String token = valueOperations.get(nickname);
            if (token == null) throw new RuntimeException("RF토큰 유효기간 만료");
            return true;
        } catch (RuntimeException ex) {
            return false;
        }
    }

    //인증 객체 생성
    public Authentication createAuthentication(String nickname){
        UserDetails userDetails = userDetailsService.loadUserByUsername(nickname);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //토큰에서 꺼내오기 기능
    public String getNicknameFromToken(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
    }
}
