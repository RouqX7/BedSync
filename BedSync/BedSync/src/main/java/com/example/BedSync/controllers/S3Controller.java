package com.example.BedSync.controllers;


import com.amazonaws.HttpMethod;
import com.example.BedSync.services.AWSS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.Map;

@RestController
@RequestMapping("/api/s3")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8081"})
public class S3Controller {

    @Autowired
    private AWSS3Service awss3Service;

    @PostMapping("/generate-presigned-url")
    public ResponseEntity<?> generatePresignedUrl(@RequestBody Map<String, String> requestBody) {
        String fileName = requestBody.get("fileName");
        String bucketName = "reservenestbucket";
        String fileKey = "uploads/" + fileName;

        URL url = awss3Service.generatePresignedUrl(bucketName, fileKey, HttpMethod.PUT, 3600); // 1 hour expiration

        return ResponseEntity.ok(url.toString());
    }
}
