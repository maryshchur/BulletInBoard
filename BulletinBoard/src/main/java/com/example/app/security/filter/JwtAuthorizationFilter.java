package com.example.app.security.filter;

import com.example.app.security.TokenManagementService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private TokenManagementService tokenManagementService;

    @Autowired
    public JwtAuthorizationFilter(TokenManagementService tokenManagementService) {
        this.tokenManagementService = tokenManagementService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = tokenManagementService.resolveAccessToken(httpServletRequest);

        if (accessToken != null) {
            try {
                if (tokenManagementService.validateToken(accessToken)) {
                    Authentication authentication =
                            tokenManagementService.getAuthentication(accessToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ExpiredJwtException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
