package com.tasksync.auth.mapper;

import com.tasksync.auth.dto.request.RegisterRequest;
import com.tasksync.auth.dto.response.UserResponse;
import com.tasksync.auth.entity.Role;
import com.tasksync.auth.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(RegisterRequest request);

    UserResponse toResponse(User user);

    default String map(Role role) {
        return role.getName().name();
    }
}