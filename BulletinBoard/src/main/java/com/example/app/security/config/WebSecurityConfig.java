package com.example.app.security.config;

import com.example.app.security.JwtAuthenticationEntryPoint;
import com.example.app.security.TokenManagementService;
import com.example.app.security.UserPrincipalDetailService;
import com.example.app.security.filter.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private TokenManagementService tokenManagementService;
    private UserPrincipalDetailService userPrincipalDetailsService;
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    public WebSecurityConfig(TokenManagementService tokenManagementService, UserPrincipalDetailService userPrincipalDetailsService,
                             JwtAuthenticationEntryPoint unauthorizedHandler) {
        this.tokenManagementService = tokenManagementService;
        this.userPrincipalDetailsService = userPrincipalDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    private static final String[] AUTH_WHITELIST = {
            "/registration",
            "/authentication"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST)
                .permitAll()
                .anyRequest()
                .authenticated();
        http
                .addFilterBefore(new JwtAuthorizationFilter(tokenManagementService), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
