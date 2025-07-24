package com.studentgradecalculator.group10projecticonicuniversity.service;

import com.studentgradecalculator.group10projecticonicuniversity.entity.User;
import com.studentgradecalculator.group10projecticonicuniversity.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = encoder;
    }

    public User registerUser(User user) {
        if (!user.getEmail().endsWith("@iconicuniversity.edu.ng")) {
            throw new IllegalArgumentException("Email must be from @iconicuniversity.edu.ng domain");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow();
    }
}
