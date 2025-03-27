package com.example.librarymanagementbackend.dto.permission.request;

import com.example.librarymanagementbackend.dto.base.request.BaseGetAllRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionGetAllRequest extends BaseGetAllRequest {
}
