package com.example.librarymanagementbackend.dto.permission.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
public class PermissionResponse {
    Long id;
    String name;
}
