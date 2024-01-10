package com.example.BedSync.serviceTests;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.BedSync.JwtTokenUtil;
import com.example.BedSync.dto.LoginDTO;
import com.example.BedSync.repos.UserRepository;
import com.example.BedSync.services.UserAuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
public class LoginServiceTest {

    @Autowired
    private UserAuthService userAuthService;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    @MockBean
    private AuthenticationManager authenticationManager;


    @Test
    public void testLoginUser() {
        // Arrange
        String testEmail = "test@example.com";
        String testPassword = "password";
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(testEmail);
        loginDTO.setPassword(testPassword);

        UserDetails mockUserDetails = new User(testEmail, testPassword, new ArrayList<>());
        when(authenticationManager.authenticate(any())).thenReturn(new TestingAuthenticationToken(mockUserDetails, null));
        when(jwtTokenUtil.generateToken(any(UserDetails.class))).thenReturn("mockJwtToken");

        // Act
        String jwtToken = userAuthService.loginUser(loginDTO);

        // Assert
        assertNotNull(jwtToken);
        verify(authenticationManager).authenticate(any());
        verify(jwtTokenUtil).generateToken(any(UserDetails.class));
    }

    // Additional tests can be added here
}
