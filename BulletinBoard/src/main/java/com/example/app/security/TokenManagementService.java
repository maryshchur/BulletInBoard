package com.example.app.security;

import com.example.app.entities.User;
import com.example.app.exceptions.JwtAuthenticationException;
import com.example.app.exceptions.NotFoundException;
import com.example.app.repository.UserRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
public class TokenManagementService {
    @Value("${jwt.token.secret}")
    private String secretKey;

    @Value("${jwt.token.expire}")
    private String expireTimeAccessToken;

    private UserPrincipalDetailService userPrincipalDetailService;

    @Autowired
    public TokenManagementService(UserPrincipalDetailService userPrincipalDetailService) {
        this.userPrincipalDetailService=userPrincipalDetailService;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String generateTokenPair(String email) {
        long nowMillis = System.currentTimeMillis();
        long expirationTime = Long.parseLong(expireTimeAccessToken);
        Date expiryDate = new Date(nowMillis + expirationTime);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }

    }

    public String resolveAccessToken(HttpServletRequest request) {
        String AUTH_HEADER_PREFIX = "Bearer ";
        String AUTHORIZATION_HEADER = "Authorization";
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (Objects.nonNull(bearerToken) && bearerToken.startsWith(AUTH_HEADER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails =userPrincipalDetailService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "",userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

}

