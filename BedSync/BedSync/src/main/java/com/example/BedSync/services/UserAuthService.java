package com.example.BedSync.services;

import com.example.BedSync.JwtTokenUtil;
import com.example.BedSync.dto.LoginDTO;
import com.example.BedSync.models.Role;
import com.example.BedSync.models.User;
import com.example.BedSync.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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


    public User registerUser(User newUser, String roleName) {
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

        return userRepository.save(newUser);
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