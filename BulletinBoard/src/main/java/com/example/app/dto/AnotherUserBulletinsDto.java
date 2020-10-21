package com.example.app.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AnotherUserBulletinsDto extends BulletinDto {
    private boolean isLikedByCurrentUser;
}
