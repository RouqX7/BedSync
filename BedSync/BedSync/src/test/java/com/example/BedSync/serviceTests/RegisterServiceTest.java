package com.example.BedSync.serviceTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.BedSync.models.User;
import com.example.BedSync.models.Role;
import com.example.BedSync.repos.UserRepository;
import com.example.BedSync.services.UserAuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class RegisterServiceTest{

    @Autowired
    private UserAuthService userAuthService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void testRegisterUser() {
        // Arrange
        User newUser = new User();
        newUser.setEmail("test@example.com");
        newUser.setPassword("Password124%");

        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        User registeredUser = userAuthService.registerUser(newUser, "DEFAULT_ROLE");

        // Assert
        assertNotNull(registeredUser);
        assertNotEquals("password", registeredUser.getPassword()); // Checks password is not plain text
        assertTrue(registeredUser.getRoles().contains(Role.DEFAULT_ROLE)); // Checks role is set
    }
}
