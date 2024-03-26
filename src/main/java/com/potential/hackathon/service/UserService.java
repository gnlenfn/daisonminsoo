package com.potential.hackathon.service;

import com.potential.hackathon.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User createUser(User user);
    // Other methods for User service
}
