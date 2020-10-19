package com.example.app.service;

import com.example.app.dto.BulletinDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BulletinService {
    Long save(String userEmail, BulletinDto bulletinDto);

    void uploadPhoto(MultipartFile photo, Long id);

    Page<BulletinDto> getAll(Integer page, Integer pageSize);

    BulletinDto getById(Long id);

    void delete(Long id);

    Page<BulletinDto> getAllByUser(Integer page, Integer pageSize,String email);

    Page<BulletinDto> getAllByUserId(Long id,Integer page, Integer pageSize);

    List<BulletinDto> getAllSubscriptionsBulletin(String username);
}
