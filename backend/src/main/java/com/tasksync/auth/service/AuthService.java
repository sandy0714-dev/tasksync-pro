package com.tasksync.auth.service;
import com.tasksync.auth.dto.request.RegisterRequest;
import com.tasksync.auth.dto.response.UserResponse;

public interface AuthService {
    UserResponse register(RegisterRequest request);
}
