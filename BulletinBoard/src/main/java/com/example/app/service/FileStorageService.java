package com.example.app.service;

import org.springframework.web.multipart.MultipartFile;


public interface FileStorageService {

    public void save(MultipartFile file);

    public String load(String filename);
}
