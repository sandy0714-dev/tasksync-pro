package com.tasksync.auth.controller;

import com.tasksync.auth.dto.request.RegisterRequest;
import com.tasksync.auth.dto.response.UserResponse;
import com.tasksync.auth.service.AuthService;
import com.tasksync.common.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        UserResponse response = authService.register(request);

        ApiResponse<UserResponse> apiResponse = new ApiResponse<>(
                true,
                "User registered successfully",
                response
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}