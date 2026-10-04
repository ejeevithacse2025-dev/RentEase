package com.rentease.service;

import com.rentease.model.User;
import com.rentease.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {

        Optional<User> existingUsername =
                userRepository.findByUsername(user.getUsername());

        if (existingUsername.isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Optional<User> existingEmail =
                userRepository.findByEmail(user.getEmail());

        if (existingEmail.isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        return userRepository.save(user);
    }

    public Optional<User> login(String username, String password) {

        Optional<User> user =
                userRepository.findByUsername(username);

        if (user.isPresent() &&
                user.get().getPassword().equals(password)) {

            return user;
        }

        return Optional.empty();
    }
}