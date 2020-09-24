package com.example.app.service;

import com.example.app.dto.UserDto;

public interface UserService {
    void register(UserDto userDto);
    void update(Long id, UserDto userDto);
    UserDto getUser(String email);
}
