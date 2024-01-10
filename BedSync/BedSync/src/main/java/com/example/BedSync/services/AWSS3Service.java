package com.example.BedSync.services;


import com.amazonaws.HttpMethod;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.Date;

@Service
public class AWSS3Service {

    private final AmazonS3 s3Client;

    public AWSS3Service() {
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion(Regions.EU_NORTH_1)
                .build();
    }

    public URL generatePresignedUrl(String bucketName, String objectKey, HttpMethod httpMethod, int expirationInSeconds) {
        Date expiration = Date.from(Instant.now().plusSeconds(expirationInSeconds));
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, objectKey)
                        .withMethod(httpMethod)
                        .withExpiration(expiration);
        return s3Client.generatePresignedUrl(generatePresignedUrlRequest);
    }

    public void uploadFile(MultipartFile file, String s3PresignedUrl) {
        try {
            // Extract bucket name and object key from the pre-signed URL
            String[] parts = s3PresignedUrl.split("/", 4);
            String bucketName = parts[2];
            String objectKey = parts[3];

            // Upload the file to S3 using the pre-signed URL
            s3Client.putObject(new PutObjectRequest(bucketName, objectKey, file.getInputStream(), null));
        } catch (IOException e) {
            throw new RuntimeException("Error uploading file to S3", e);
        }
    }
}
