package com.example.librarymanagementbackend.mapper;

import com.example.librarymanagementbackend.dto.role.request.RoleRequest;
import com.example.librarymanagementbackend.dto.role.request.RoleUpdateRequest;
import com.example.librarymanagementbackend.dto.role.response.RoleResponse;
import com.example.librarymanagementbackend.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role role);

    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateRole(@MappingTarget Role role, RoleUpdateRequest request);
}
