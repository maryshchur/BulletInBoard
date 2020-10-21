package com.example.app.service.impl;

import com.example.app.dto.AnotherUserProfileDto;
import com.example.app.dto.UserDto;
import com.example.app.entities.Bulletin;
import com.example.app.entities.User;
import com.example.app.exceptions.NotFoundException;
import com.example.app.exceptions.NotSavedException;
import com.example.app.repository.BulletinRepository;
import com.example.app.repository.UserRepository;
import com.example.app.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;
    private BulletinRepository bulletinRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder, ModelMapper modelMapper,
                           BulletinRepository bulletinRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.bulletinRepository=bulletinRepository;
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
        if (!getUser(user.getId()).getPassword().equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        saveOrUpdate(user);
    }

    @Override
    public UserDto getUser(Long id) {
        return modelMapper.map(findById(id), UserDto.class);
    }

    @Override
    public AnotherUserProfileDto getAnotherUser(Long id) {
        User user = findById(id);
        return modelMapper.map(user,AnotherUserProfileDto.class);
    }


    private User findByEmail(String email) {
        return userRepository.findUserByEmail(email).
                orElseThrow(() -> new NotFoundException(String.format("User with %s email was not found", email)));
    }

    private User findById(Long id) {
        return userRepository.findUserById(id).
                orElseThrow(() -> new NotFoundException(String.format("User with %d id was not found", id)));
    }

    @Override
    public void subscribe(String subscriberEmail,Long userToSubscribeId){
        User newSubscriber = findByEmail(subscriberEmail);
        User user =findById(userToSubscribeId);
        Set<User> subscribers = user.getSubscribers();
        if(subscribers.contains(newSubscriber)){
            subscribers.remove(newSubscriber);
        } else {
            subscribers.add(newSubscriber);
        }
        userRepository.save(user);
    }

    @Override
    public Set<UserDto> getSubscribers(Long id) {
        return findById(id).getSubscribers().stream()
                .map(x->modelMapper.map(x,UserDto.class))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<UserDto> getSubscriptions(Long id) {
        return findById(id).getSubscriptions().stream()
                .map(x->modelMapper.map(x,UserDto.class))
                .collect(Collectors.toSet());
    }

    @Override
    public void likeBulletin(String username, Long id) {
        User user= findByEmail(username);
        Bulletin bulletin=bulletinRepository.findById(id).get();
        Set<Bulletin> allLikedBulletins=user.getLikedBulletins();
        if(allLikedBulletins.contains(bulletin)){
            allLikedBulletins.remove(bulletin);
            bulletin.getLikes().remove(user);
        } else{
            allLikedBulletins.add(bulletin);
            bulletin.getLikes().add(user);
        }
        userRepository.save(user);
    }

}
