package com.example.app.dto;

import com.example.app.entities.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.sql.Date;
import java.time.LocalDateTime;
@Data
public class BulletinDto {
    private String title;

    private String image;

    private String description;

    private LocalDateTime addedDate;

    private User user;
}
