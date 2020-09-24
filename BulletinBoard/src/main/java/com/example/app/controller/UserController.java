package com.example.app.controller;

import com.example.app.dto.LoginedUserDto;
import com.example.app.dto.UserDto;
import com.example.app.security.AuthenticationService;
import com.example.app.security.UserPrincipal;
import com.example.app.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@Validated
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private UserService userService;
    private AuthenticationService authenticationService;
    private String AUTHORIZATION_HEADER = "authorization";
    private String AUTH_HEADER_PREFIX = "Bearer ";

    @Autowired
    public UserController(UserService userService,
                          AuthenticationService authenticationService
                          ) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/registration")
    public ResponseEntity register(@Valid @RequestBody UserDto userDto) {
        userService.register(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/authentication")
    public ResponseEntity<String> login(@RequestBody @Valid LoginedUserDto loginUser, HttpServletResponse response) {

        String s = authenticationService.loginUser(loginUser);
       // response.setHeader(AUTHORIZATION_HEADER, AUTH_HEADER_PREFIX + s);
        return ResponseEntity.status(HttpStatus.OK).body(s);
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @PutMapping("/profile/{id}")
    public ResponseEntity update(@PathVariable Long id,  @RequestBody UserDto userDto){
        userService.update(id, userDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @GetMapping("/profile")
    public ResponseEntity<UserDto> getProfileData(
            @ApiIgnore @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(principal.getUsername()));
    }
}
