package com.example.app.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class BulletinDto
        extends CreateBulletinDto
{

    private Long id;

    private String image;

    private LocalDateTime addedDate;

    private UserInfoDto user;

    private int amountOfLikes;
}
