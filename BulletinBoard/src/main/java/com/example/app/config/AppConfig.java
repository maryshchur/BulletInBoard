package com.example.app.config;

import com.example.app.dto.AnotherUserProfileDto;
import com.example.app.dto.BulletinDto;
import com.example.app.dto.UserDto;
import com.example.app.entities.Bulletin;
import com.example.app.entities.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

@Configuration
public class AppConfig {
    @Value("${ENDPOINT_URL}")
    private String endpointUrl;

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.typeMap(Bulletin.class, BulletinDto.class).setPostConverter(context ->
        {
            BulletinDto bulletinDto = context.getDestination();
            bulletinDto.setImage(endpointUrl + context.getSource().getImage());
            return bulletinDto;
        });
        modelMapper.typeMap(User.class, AnotherUserProfileDto.class).setPostConverter(context ->
        {
            AnotherUserProfileDto userDto = context.getDestination();
            userDto.setAmountOfSubscribers(context.getSource().getSubscribers().size());
            userDto.setAmountOfSubscriptions(context.getSource().getSubscriptions().size());
            return userDto;
        }).include(UserDto.class);
        return modelMapper;
    }
}
