package com.login.auth.service;

import com.login.auth.exception.InvalidTokenException;
import com.login.domain.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    public String createToken(User user) {
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("loginService")
                .setExpiration(new Date(new Date().getTime() + 5000))
                .claim("id", user.getId())
                .signWith(SignatureAlgorithm.HS256, "secret")
                .compact();
    }

    public Long getIdByAuth(String auth) {
        return Long.parseLong(parseToken(auth).getId());
    }

    private Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey("secret")
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new InvalidTokenException();
        }
    }

    private String getToken(String auth) {
        if (auth == null || !auth.startsWith("Bearer ")) {
            throw new InvalidTokenException();
        }
        return auth.substring("Bearer ".length());
    }
}
