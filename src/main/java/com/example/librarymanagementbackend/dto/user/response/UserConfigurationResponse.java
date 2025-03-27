package com.example.librarymanagementbackend.dto.user.response;

import com.example.librarymanagementbackend.constants.UserRole;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserConfigurationResponse {
    Long roleId;
    UserRole roleName;

    AuthPermissions auth;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class AuthPermissions {
        Map<String, Boolean> allPermissions;
        Map<String, Boolean> grantedPermissions;
    }
}