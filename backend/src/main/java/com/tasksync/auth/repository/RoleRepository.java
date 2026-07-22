package com.tasksync.auth.repository;

import com.tasksync.auth.entity.Role;
import com.tasksync.common.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName roleName);

}