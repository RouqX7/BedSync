package com.example.BedSync.controllers;

import com.example.BedSync.models.Role;
import com.example.BedSync.models.User;
import com.example.BedSync.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8081"})
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        Iterable<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        User user = userService.findUserById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/assign-role/{userId}")
    public ResponseEntity<User> assignRole(@PathVariable String userId, @RequestParam Role role) {
        userService.assignRole(userId, role);
        User updatedUser = userService.findUserById(userId);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/change-password/{userId}")
    public ResponseEntity<User> changePassword(@PathVariable String userId, @RequestParam String newPassword) {
        userService.changePassword(userId, newPassword);
        User updatedUser = userService.findUserById(userId);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/search")
    public ResponseEntity<Optional<User>> searchUsersByEmail(@RequestParam String query) {
        Optional<User> users = userService.searchUsersByEmail(query);
        return ResponseEntity.ok(users);
    }


    // Add other user-related endpoints as needed (update, delete, find, etc.).
}