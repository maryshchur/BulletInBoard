package com.example.app.controller;

import com.example.app.dto.*;
import com.example.app.security.AuthenticationService;
import com.example.app.security.UserPrincipal;
import com.example.app.service.UserService;
import com.example.app.util.validation.IsCurrentUserBulletin;
import com.example.app.util.validation.isCurrentUser;
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
import java.util.List;
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
    public ResponseEntity<AuthenticationDetailsDto> login(@RequestBody @Valid LoginedUserDto loginUser, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.loginUser(loginUser));
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @PutMapping("/profile/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody UserDto userDto) {
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
    public ResponseEntity<AnotherUserProfileDto> getUserProfileData(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAnotherUser(id));
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @PutMapping("/profile/subscribe/{userId}")
    public ResponseEntity changeSubscription(@AuthenticationPrincipal @ApiIgnore UserPrincipal principal,
                                             @PathVariable @isCurrentUser Long userId) {
        if (!principal.getUser().getId().equals(userId)) {
            userService.subscribe(principal.getUsername(), userId);
        }
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @GetMapping("/profile/subscribers/{id}")
    public ResponseEntity<Set<UserDto>> getSubscribers(@AuthenticationPrincipal @ApiIgnore UserPrincipal principal,
                                                       @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getSubscribers(id));
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @GetMapping("/profile/subscription/{id}")
    public ResponseEntity<Set<UserDto>> getSubscriptions(@AuthenticationPrincipal @ApiIgnore UserPrincipal principal,
                                                         @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getSubscriptions(id));

    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @GetMapping("/profile/subscribers")
    public ResponseEntity<Set<UserDto>> getSubscribersForCurrentUser(@AuthenticationPrincipal @ApiIgnore UserPrincipal principal
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getSubscribers(principal.getUser().getId()));
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @GetMapping("/profile/subscription ")
    public ResponseEntity<Set<UserDto>> getSubscriptionsForCurrentUser(@AuthenticationPrincipal @ApiIgnore UserPrincipal principal
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getSubscriptions(principal.getUser().getId()));

    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "JWT")})
    @PutMapping("/bulletin/{id}")
    public ResponseEntity likeBulletin(@ApiIgnore @AuthenticationPrincipal UserPrincipal principal,
                                                          @PathVariable  @IsCurrentUserBulletin Long id) {
        userService.likeBulletin(principal.getUsername(),id);
        return ResponseEntity.status(HttpStatus.OK).build();

    }
}
