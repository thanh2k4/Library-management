package com.example.librarymanagementbackend.dto.user.response;

import com.example.librarymanagementbackend.constants.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    String name;
    String userName;
    String email;
    Date createdAt;
    Date updatedAt;
    Long roleId;
    @Enumerated(EnumType.STRING)
    UserRole roleName;
}
