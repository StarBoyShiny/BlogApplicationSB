package com.orgPRO.BlogApplication.services.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.orgPRO.BlogApplication.security.BlogUserDetailsService;
import com.orgPRO.BlogApplication.services.AuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final BlogUserDetailsService userDetailsService;

    @Value("${jwt.secrete}")
    private String secretKey;

    private final long jwtExpiryMs = 8640000;

    @Override
    public UserDetails authenticate(String email, String password) {
        System.out.println("i am from Impl");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return userDetails;
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        HashMap<String, Object> claims = new HashMap<>();
        String compact = Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiryMs))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();

        return compact;

    }

    @Override
    public UserDetails validateToken(String token) {
        String s = extractUsername(token);
        return userDetailsService.loadUserByUsername(s);
    }

    private String extractUsername(String token) {
        Claims body = Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return body.getSubject();
    }

    private Key getSigninKey() {
        byte[] bytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(bytes);
    }
}
