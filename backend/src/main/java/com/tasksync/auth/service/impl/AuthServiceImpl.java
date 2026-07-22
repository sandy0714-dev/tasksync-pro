package com.tasksync.auth.service.impl;

import com.tasksync.auth.dto.request.RegisterRequest;
import com.tasksync.auth.dto.response.UserResponse;
import com.tasksync.auth.entity.Role;
import com.tasksync.auth.mapper.UserMapper;
import com.tasksync.auth.repository.RoleRepository;
import com.tasksync.auth.repository.UserRepository;
import com.tasksync.auth.service.AuthService;
import com.tasksync.common.enums.RoleName;
import com.tasksync.common.exceptions.DuplicateResourceException;
import com.tasksync.common.exceptions.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.tasksync.auth.entity.User;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        Role employeeRole = roleRepository.findByName(RoleName.EMPLOYEE)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Default role EMPLOYEE not found"));

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(employeeRole));
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }
}