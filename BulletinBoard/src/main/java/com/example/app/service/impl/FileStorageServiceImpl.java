package com.example.app.service.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.app.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private AmazonS3 s3client;
    @Value("${BUCKET_NAME}")
    private String bucketName;
    @Value("${ACCESS_KEY}")
    private String accessKey;
    @Value("${SECRET_ACCESS_KEY}")
    private String secretAccessKey;
    @Value("${ENDPOINT_URL}")
    private String endpointUrl;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretAccessKey);
        s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_CENTRAL_1).build();
    }

    private File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        try(OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        }
        return file;
    }

    private String generateFileName() {
        return UUID.randomUUID().toString();
    }

    private void uploadFileToS3(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    @Override
    @Transactional
    public String uploadFile(MultipartFile multipartFile){
        String fileName = "";
        try {
            fileName = generateFileName();
            uploadFileToS3(fileName, convertMultipartToFile(multipartFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }


}
