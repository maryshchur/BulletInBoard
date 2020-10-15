package com.example.app.config;

import com.example.app.dto.BulletinDto;
import com.example.app.entities.Bulletin;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        return modelMapper;
    }
}
