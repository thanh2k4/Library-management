package com.example.librarymanagementbackend.dto.user.request;

import com.example.librarymanagementbackend.dto.base.request.BaseGetAllRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserGetAllRequest extends BaseGetAllRequest {
    String name;
    String Email;
    Long roleId;
}
