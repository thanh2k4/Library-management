package com.example.librarymanagementbackend.repository;

import com.example.librarymanagementbackend.constants.UserRole;
import com.example.librarymanagementbackend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(UserRole name);
}
