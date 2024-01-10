package com.example.BedSync.services;

import com.example.BedSync.models.Role;
import com.example.BedSync.models.User;
import com.example.BedSync.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User findUserById(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void changePassword(String userId, String newPassword) {
        User user = findUserById(userId);
        if (user != null) {
            user.setPassword(newPassword); // Replace with hashed password
            userRepository.save(user);
        };
    }

    public void resetPassword(String email) {
        return;
    }

    public void assignRole(String userId, Role role) {
        User user = findUserById(userId);
        if (user != null) {
            user.setRole(role);
            userRepository.save(user);
        }
    }

    public boolean setSecurityQuestionAnswer(String userId, String question, String answer) {
        User user = findUserById(userId);
        if (user != null) {
            user.setSecurityQuestion(question);
            user.setSecurityAnswer(answer); // Consider encrypting the answer
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean verifySecurityQuestionAnswer(String userId, String answer) {
        User user = findUserById(userId);
        return user != null && user.getSecurityAnswer().equals(answer);
    }
    public Optional<User> searchUsersByEmail(String query) {
        return userRepository.findByEmail(query);
    }

}