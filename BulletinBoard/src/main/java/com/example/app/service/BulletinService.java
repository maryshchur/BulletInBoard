package com.example.app.service;

import com.example.app.dto.AnotherUserBulletinsDto;
import com.example.app.dto.BulletinDto;
import com.example.app.dto.CreateBulletinDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BulletinService {
    Long save(String userEmail, CreateBulletinDto createBulletinDto);

    void uploadPhoto(MultipartFile photo, Long id);

    Page<AnotherUserBulletinsDto> getAll(Integer page, Integer pageSize);

    AnotherUserBulletinsDto getById(Long id);

    void delete(Long id);

    Page<BulletinDto> getAllByUser(Integer page, Integer pageSize, String email);

    Page<AnotherUserBulletinsDto> getAllByUserId(Long id, Integer page, Integer pageSize);

    List<AnotherUserBulletinsDto> getAllSubscriptionsBulletin(String username);

    List<BulletinDto> getAllLikedBulletin(String username);
}

