package com.example.librarymanagementbackend.mapper;

import com.example.librarymanagementbackend.dto.permission.request.PermissionRequest;
import com.example.librarymanagementbackend.dto.permission.request.PermissionUpdateRequest;
import com.example.librarymanagementbackend.dto.permission.response.PermissionResponse;
import com.example.librarymanagementbackend.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);

    @Mapping(target = "id", ignore = true)
    void updatePermission(@MappingTarget Permission permission, PermissionUpdateRequest request);
}
