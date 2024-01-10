package com.example.BedSync.controllers;

import com.example.BedSync.AuthToken;
import com.example.BedSync.JwtTokenUtil;
import com.example.BedSync.dto.LoginDTO;
import com.example.BedSync.models.User;
import com.example.BedSync.services.UserAuthService;
import com.example.BedSync.services.UserService;
import com.example.BedSync.services.ValidationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8081"})
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

    @PostMapping("/register-admin")
    public ResponseEntity<Void> registerAdmin(@RequestBody User newAdmin, HttpServletRequest request) {
        try {
            logRequest(newAdmin);

            User registeredAdmin = userAuthService.registerAdmin(newAdmin);

            logBeforeAndAfterRegistration(registeredAdmin);

            if (registeredAdmin != null) {
                return createResponseEntityWithLocation(request, registeredAdmin.getId());
            } else {
                logRegistrationFailure();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logException(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void logRequest(User user) {
        System.out.println("Received request in registerAdmin method");
        System.out.println("User details: " + user);
    }

    private void logBeforeAndAfterRegistration(User registeredUser) {
        System.out.println("Before registration");
        System.out.println("After registration. Registered user: " + registeredUser);
    }

    private ResponseEntity<Void> createResponseEntityWithLocation(HttpServletRequest request, String userId) {
        URI location = ServletUriComponentsBuilder.fromRequestUri(request)
                .path("/{id}")
                .buildAndExpand(userId)
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    private void logRegistrationFailure() {
        System.out.println("Registration failed. The registered user object is null.");
    }

    private void logException(Exception e) {
        e.printStackTrace();
    }
}