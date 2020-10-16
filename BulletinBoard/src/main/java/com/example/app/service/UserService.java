package com.example.app.service;

import com.example.app.dto.UserDto;

import java.util.Set;

public interface UserService {
    void register(UserDto userDto);
    void update(Long id, UserDto userDto);
    UserDto getUser(String email);
    void subscribe(String subscriberEmail,Long userToSubscribeId);
    Set<UserDto> getSubscribers(Long id);
    Set<UserDto> getSubscriptions(Long id);
}
