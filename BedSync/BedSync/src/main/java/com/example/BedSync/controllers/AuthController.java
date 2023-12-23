package com.example.BedSync.controllers;

import com.example.BedSync.AuthToken;
import com.example.BedSync.JwtTokenUtil;
import com.example.BedSync.dto.LoginDTO;
import com.example.BedSync.models.User;
import com.example.BedSync.services.UserAuthService;
import com.example.BedSync.services.UserService;
import com.example.BedSync.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private ValidationService validationService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            String jwt = userAuthService.loginUser(loginDTO);
            return ResponseEntity.ok(new AuthToken(jwt));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<?> createNewUser(@RequestBody User newUser) {
        Optional<User> userExists = userService.findUserByEmail(newUser.getEmail());
        if (userExists.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("There is already a user registered with the email provided");
        }

        userAuthService.registerUser(newUser, "DEFAULT_ROLE"); // Assuming default role
        return ResponseEntity.ok("User has been registered successfully");
    }

}
