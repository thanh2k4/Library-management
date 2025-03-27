package com.example.librarymanagementbackend.dto.role.response;

import com.example.librarymanagementbackend.constants.UserRole;
import com.example.librarymanagementbackend.entity.Permission;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
public class RoleResponse {
    Long id;
    @Enumerated(EnumType.STRING)
    UserRole name;
    Set<Permission> permissions;
}
