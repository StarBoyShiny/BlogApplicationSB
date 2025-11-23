package com.orgPRO.BlogApplication.services.impl;

import com.orgPRO.BlogApplication.domain.entities.User;
import com.orgPRO.BlogApplication.repositories.UserRepository;
import com.orgPRO.BlogApplication.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getUserById(UUID userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
    }
}
