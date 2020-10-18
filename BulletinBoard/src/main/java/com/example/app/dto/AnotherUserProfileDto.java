package com.example.app.dto;

import lombok.Data;

@Data
public class AnotherUserProfileDto extends UserDto {
    private boolean isCurrentUserSubscriber;
}
