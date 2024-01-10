package com.example.BedSync.serviceTests;

import com.example.BedSync.models.Role;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.example.BedSync.models.User;
import com.example.BedSync.repos.UserRepository;
import com.example.BedSync.services.UserAuthService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class UserAuthServiceTests {

    @Autowired
    private UserAuthService userAuthService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Test
    public void testRegisterAdmin() {
        // Arrange
        User newAdmin = new User();
        newAdmin.setEmail("admin@example.com");
        newAdmin.setPassword("Password124%");
        newAdmin.setRole(Role.ADMIN);

        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        User registeredAdmin = userAuthService.registerAdmin(newAdmin);

        // Assert
        assertNotNull(registeredAdmin);
        assertNotEquals("password", registeredAdmin.getPassword()); // Checks password is not plain text
        assertTrue(registeredAdmin.getRoles().contains(Role.ADMIN)); // Checks role is set to ADMIN
    }


    // Other test cases...
}

