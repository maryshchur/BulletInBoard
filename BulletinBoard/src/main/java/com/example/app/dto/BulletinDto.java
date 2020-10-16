package com.example.app.dto;

import com.example.app.entities.User;
import lombok.Data;
import java.time.LocalDateTime;
@Data
public class BulletinDto {
    private String title;

    private String image;

    private String description;

    private LocalDateTime addedDate;

    private User user;
}
