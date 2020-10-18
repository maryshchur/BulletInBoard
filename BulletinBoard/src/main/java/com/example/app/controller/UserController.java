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
import java.util.Set;

@RestController
@Validated
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private UserService userService;
    private AuthenticationService authenticationService;

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
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(principal.getUser().getId()));
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @GetMapping("/profile/user/{id}")
    public ResponseEntity<UserDto> getUserProfileData(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(id));
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @PutMapping("/profile/subscribe/{userId}")
    public ResponseEntity changeSubscription(@AuthenticationPrincipal @ApiIgnore UserPrincipal principal,
                                    @PathVariable Long userId){
        if(!principal.getUser().getId().equals(userId)){
            userService.subscribe(principal.getUsername(),userId);
        }
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @GetMapping("/profile/subscribers/{id}")
    public ResponseEntity<Set<UserDto>> getSubscribers(@AuthenticationPrincipal @ApiIgnore UserPrincipal principal,
                                                           @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getSubscribers(id));
    }
    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @GetMapping("/profile/subscription/{id}")
    public ResponseEntity<Set<UserDto>> getSubscriptions(@AuthenticationPrincipal @ApiIgnore UserPrincipal principal,
                                             @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getSubscriptions(id));

    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @GetMapping("/profile/subscribers")
    public ResponseEntity<Set<UserDto>> getSubscribersForCurrentUser(@AuthenticationPrincipal @ApiIgnore UserPrincipal principal
                                                    ){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getSubscribers(principal.getUser().getId()));
    }
    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @GetMapping("/profile/subscription ")
    public ResponseEntity<Set<UserDto>> getSubscriptionsForCurrentUser(@AuthenticationPrincipal @ApiIgnore UserPrincipal principal
                                                        ){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getSubscriptions(principal.getUser().getId()));

    }
}
