package com.example.app.service.impl;

import com.example.app.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageServiceImpl implements FileStorageService {
   // private final Path root = Paths.get("uploads");
   @Value("${app.upload.dir:${user.home}}")
   public String uploadDir;
    private final Path root = Paths.get("src/main/resources/static");
    private static final String CLASSPATH_RESOURCE_LOCATIONS = "classpath:/resources/";
//
//    @PostConstruct
//    public void init() {
//        try {
//            if (!Files.exists(root)) {
//                Files.createDirectory(root);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("Could not initialize folder for upload!");
//        }
//    }

    @Override
    public void save(MultipartFile file) {
        try {
//            Path copyLocation = Paths
//                    .get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
//            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));

        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }

    }

    @Override
    public String load(String filename) {
//        try {
////            Path file = root.resolve(filename);
////            Resource resource = new UrlResource(file.toUri());
////
////            if (resource.exists() || resource.isReadable()) {
////                return resource.getURL().toString();
////            } else {
////                throw new RuntimeException("Could not read the file!");
////            }
//            return FileStorageServiceImpl.class.getResource("/static/"+filename).getFile();
//        } catch (Exception e) {
//            throw new RuntimeException("Error: " + e.getMessage());
//        }
        return filename;
    }
}
