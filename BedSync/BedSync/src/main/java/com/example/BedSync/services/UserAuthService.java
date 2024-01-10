package com.example.BedSync.services;

import com.amazonaws.HttpMethod;
import com.example.BedSync.JwtTokenUtil;
import com.example.BedSync.dto.LoginDTO;
import com.example.BedSync.models.Role;
import com.example.BedSync.models.User;
import com.example.BedSync.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

@Service
public class UserAuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private AWSS3Service awsS3Service; // Make sure to import the AWSS3Service class



    public User registerUser(User newUser, String roleName) {
        try {
            // Validate user data
            if (!validationService.isValidEmail(newUser.getEmail())) {
                throw new IllegalArgumentException("Invalid email format");
            }
            if (!validationService.isValidPassword(newUser.getPassword())) {
                throw new IllegalArgumentException("Invalid password format");
            }

            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            Role userRole = Role.valueOf(roleName.toUpperCase());
            newUser.setRoles(new HashSet<>(Collections.singletonList(userRole))); // Set the role

            // Generate pre-signed URL for S3
            String fileName = UUID.randomUUID().toString() + "_" + newUser.getImage().getOriginalFilename();
            String s3PresignedUrl = String.valueOf(awsS3Service.generatePresignedUrl("reservenestbucket", "uploads/" + fileName, HttpMethod.PUT, 3600).toString());

            // Upload image to S3
            awsS3Service.uploadFile(newUser.getImage(), s3PresignedUrl);

            // Set the image URL in the user object
            newUser.setProfilePictureUrl(s3PresignedUrl);

            return userRepository.save(newUser);
        } catch (Exception e) {
            // Handle exceptions
            throw new RuntimeException("Error registering user", e);
        }
    }


    public User registerAdmin(User newAdmin) {
        Role adminRole = Role.ADMIN;

        // Validate user data
        if (!validationService.isValidEmail(newAdmin.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!validationService.isValidPassword(newAdmin.getPassword())) {
            throw new IllegalArgumentException("Invalid password format");
        }

        newAdmin.setPassword(bCryptPasswordEncoder.encode(newAdmin.getPassword()));
        newAdmin.setRoles(new HashSet<>(Collections.singletonList(adminRole))); // Set the role

        String fileName = UUID.randomUUID().toString() + "_" + newAdmin.getImage().getOriginalFilename();
        String s3PresignedUrl = String.valueOf(awsS3Service.generatePresignedUrl("reservenestbucket", "uploads/" + fileName, HttpMethod.PUT, 3600).toString());
        // Upload the file to S3
        awsS3Service.uploadFile(newAdmin.getImage(), s3PresignedUrl);


        try {
            return userRepository.save(newAdmin);
        } catch (DataIntegrityViolationException e) {
            // Handle the case where a user with the same email already exists
            throw new IllegalArgumentException("User with the same email already exists");
        } catch (Exception e) {
            // Handle other exceptions
            throw new RuntimeException("Error saving admin user", e);
        }
    }


    public String loginUser(LoginDTO loginDTO) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());
    }

}