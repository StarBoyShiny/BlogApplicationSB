package com.orgPRO.BlogApplication.services;

import com.orgPRO.BlogApplication.domain.entities.User;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID userId);
}
