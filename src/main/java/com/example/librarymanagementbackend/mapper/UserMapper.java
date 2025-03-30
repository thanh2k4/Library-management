package com.example.librarymanagementbackend.mapper;

import com.example.librarymanagementbackend.dto.user.request.UserCreationRequest;
import com.example.librarymanagementbackend.dto.user.request.UserUpdateRequest;
import com.example.librarymanagementbackend.dto.user.response.UserResponse;
import com.example.librarymanagementbackend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toUser(UserCreationRequest request);
    @Mapping(target = "roleId", ignore = true)
    @Mapping(target = "roleName", ignore = true)
    UserResponse toUserResponse(User user);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
