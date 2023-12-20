package com.example.BedSync.services;

import com.example.BedSync.models.Role;
import com.example.BedSync.models.User;
import com.example.BedSync.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
public class UserAuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User registerUser(User newUser, String roleName) {
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

        Role userRole = Role.valueOf(roleName.toUpperCase());
        newUser.setRoles(new HashSet<>(Collections.singletonList(userRole))); // Set the role
        return userRepository.save(newUser);
    }
}