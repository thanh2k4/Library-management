package com.example.librarymanagementbackend.dto.permission.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
public class PermissionUpdateRequest {
    Long id;
    String name;
}
