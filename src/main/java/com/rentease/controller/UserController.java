package com.rentease.controller;

import com.rentease.model.User;
import com.rentease.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        try {
            User savedUser = userService.register(user);

            return ResponseEntity.ok(savedUser);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        Optional<User> loggedInUser =
                userService.login(
                        user.getUsername(),
                        user.getPassword()
                );

        if (loggedInUser.isPresent()) {
            return ResponseEntity.ok(loggedInUser.get());
        }

        return ResponseEntity
                .badRequest()
                .body("Invalid username or password");
    }
}