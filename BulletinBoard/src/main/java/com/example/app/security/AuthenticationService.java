package com.example.app.security;

import com.example.app.dto.AuthenticationDetailsDto;
import com.example.app.dto.LoginedUserDto;
import com.example.app.entities.User;
import com.example.app.exceptions.BadCredentialException;
import com.example.app.repository.UserRepository;
import com.example.app.security.config.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationService {

    private TokenManagementService tokenManagementService;
    private UserRepository userRepository;
    private WebSecurityConfig webSecurityConfig;

    @Autowired
    public AuthenticationService(TokenManagementService tokenManagementService,
                                 UserRepository userRepository,
                                 WebSecurityConfig webSecurityConfig) {
        this.tokenManagementService = tokenManagementService;
        this.userRepository = userRepository;
        this.webSecurityConfig = webSecurityConfig;
    }

    public AuthenticationDetailsDto loginUser(LoginedUserDto loginUser) {

        Optional<User> user = userRepository.findUserByEmail(loginUser.getEmail());
        PasswordEncoder passwordEncoder = webSecurityConfig.passwordEncoder();
        if (passwordEncoder.matches(loginUser.getPassword(),
                user.orElseThrow(() ->
                        new BadCredentialException(String.format("User with %s email does not exist", loginUser.getEmail())))
                        .getPassword())) {
            return new AuthenticationDetailsDto(tokenManagementService.generateTokenPair(loginUser.getEmail()),user.get().getId());
        } else {
            throw new BadCredentialException("Wrong password");
        }
    }
}
