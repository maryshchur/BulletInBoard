package com.example.app.service.impl;

import com.example.app.dto.UserDto;
import com.example.app.entities.User;
import com.example.app.exceptions.NotFoundException;
import com.example.app.exceptions.NotSavedException;
import com.example.app.repository.UserRepository;
import com.example.app.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public void register(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        saveOrUpdate(user);
    }

    private void saveOrUpdate(User user) {
        if (userRepository.save(user) == null) {
            throw new NotSavedException(String.format("User with %s email was not saved or updated", user.getEmail()));
        }
    }

    @Override
    public void update(Long id, UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setId(id);
        if (!getUser(user.getEmail()).getPassword().equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        saveOrUpdate(user);
    }

    @Override
    public UserDto getUser(String email) {
        return modelMapper.map(userRepository.findUserByEmail(email).
                orElseThrow(() -> new NotFoundException(String.format("User with %s email was not found", email))), UserDto.class);
    }


}
