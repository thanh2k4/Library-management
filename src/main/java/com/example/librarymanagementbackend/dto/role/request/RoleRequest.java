package com.example.librarymanagementbackend.dto.role.request;

import com.example.librarymanagementbackend.constants.UserRole;
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
public class RoleRequest {
    @Enumerated(EnumType.STRING)
    UserRole name;
    Set<Long> permissions;
}
