package com.example.app.dto;

import lombok.Data;
import java.time.LocalDateTime;
@Data
public class BulletinDto {
    private Long id;
    private String title;

    private String image;

    private String description;

    private LocalDateTime addedDate;

    private UserInfoDto user;
}
