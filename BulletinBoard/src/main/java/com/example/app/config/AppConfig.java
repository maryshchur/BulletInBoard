package com.example.app.config;

import com.example.app.dto.AnotherUserBulletinsDto;
import com.example.app.dto.AnotherUserProfileDto;
import com.example.app.dto.BulletinDto;
import com.example.app.dto.UserDto;
import com.example.app.entities.Bulletin;
import com.example.app.entities.User;
import com.example.app.exceptions.NotFoundException;
import com.example.app.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

@Configuration
public class AppConfig {
    @Value("${ENDPOINT_URL}")
    private String endpointUrl;
    private UserRepository userRepository;

    @Autowired
   public AppConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Bulletin.class, BulletinDto.class).setPostConverter(context ->
        {
            BulletinDto bulletinDto = context.getDestination();
            bulletinDto.setImage(endpointUrl + context.getSource().getImage());
            bulletinDto.setAmountOfLikes(context.getSource().getLikes().size());
            return bulletinDto;
        });

        //TODO read again about typeMap inheritance to delete repetitive code
        modelMapper.typeMap(Bulletin.class, AnotherUserBulletinsDto.class).setPostConverter(context ->
        {
            AnotherUserBulletinsDto bulletinDto = context.getDestination();
            bulletinDto.setImage(endpointUrl + context.getSource().getImage());
            bulletinDto.setAmountOfLikes(context.getSource().getLikes().size());
            Principal principal = SecurityContextHolder.getContext().getAuthentication();
            bulletinDto.setLikedByCurrentUser(context.getSource().getLikes()
                    .contains(userRepository.findUserByEmail(principal.getName()).get()));
            return bulletinDto;
        });

        modelMapper.typeMap(User.class, UserDto.class).setPostConverter(context ->
        {
            UserDto userDto = context.getDestination();
            userDto.setAmountOfSubscribers(context.getSource().getSubscribers().size());
            userDto.setAmountOfSubscriptions(context.getSource().getSubscriptions().size());
            return userDto;
        });

        modelMapper.typeMap(User.class, AnotherUserProfileDto.class).setPostConverter(context ->
        {
            AnotherUserProfileDto userProfileDto = context.getDestination();
            userProfileDto.setAmountOfSubscribers(context.getSource().getSubscribers().size());
            userProfileDto.setAmountOfSubscriptions(context.getSource().getSubscriptions().size());
            Principal principal = SecurityContextHolder.getContext().getAuthentication();
            userProfileDto.setCurrentUserSubscriber(context.getSource()
                    .getSubscribers().contains(userRepository.findUserByEmail(principal.getName()).get()));
            return userProfileDto;
        });
        return modelMapper;
    }
}
