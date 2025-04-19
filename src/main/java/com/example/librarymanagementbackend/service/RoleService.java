package com.example.librarymanagementbackend.service;

import com.example.librarymanagementbackend.dto.base.response.BaseGetAllResponse;
import com.example.librarymanagementbackend.dto.role.request.RoleRequest;
import com.example.librarymanagementbackend.dto.role.request.RoleUpdateRequest;
import com.example.librarymanagementbackend.dto.role.response.RoleResponse;
import com.example.librarymanagementbackend.entity.Permission;
import com.example.librarymanagementbackend.entity.Role;
import com.example.librarymanagementbackend.exception.AppException;
import com.example.librarymanagementbackend.exception.ErrorCode;
import com.example.librarymanagementbackend.mapper.RoleMapper;
import com.example.librarymanagementbackend.repository.PermissionRepository;
import com.example.librarymanagementbackend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@Slf4j
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public RoleResponse createRole(RoleRequest request) {
        Role role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllById(
                request.getPermissions().stream().map(Long::valueOf).collect(Collectors.toSet()));
        if (permissions.size() != request.getPermissions().size()) {
            throw new AppException(ErrorCode.PERMISSION_NOT_EXISTED);
        }
        role.setPermissions(new HashSet<>(permissions));
        try {
            role = roleRepository.save(role);
        } catch (Exception exception) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }

        return roleMapper.toRoleResponse(role);
    }

    @PreAuthorize("hasAuthority('Role.GetAll')")
    public BaseGetAllResponse<RoleResponse> getAllRole() {
        List<RoleResponse> roleList = roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();

        return BaseGetAllResponse.<RoleResponse>builder()
                .data(roleList)
                .totalRecords(roleRepository.count())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public RoleResponse getRoleById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        return roleMapper.toRoleResponse(role);

    }

    @PreAuthorize("hasRole('ADMIN')")
    public RoleResponse updateRole(RoleUpdateRequest request) {
        Role role = roleRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        roleMapper.updateRole(role, request);
        var permissions = permissionRepository.findAllById(
                request.getPermissions().stream().map(Long::valueOf).collect(Collectors.toSet()));
        if (permissions.size() != request.getPermissions().size()) {
            throw new AppException(ErrorCode.PERMISSION_NOT_EXISTED);
        }
        role.setPermissions(new HashSet<>(permissions));
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteRole(Long id) {
        Role Role = roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        roleRepository.delete(Role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public RoleResponse addPermissionstoRole(Long roleId, List<Long> permissionIds) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        try {
            var permissions = permissionRepository.findAllById(
                    permissionIds.stream().map(Long::valueOf).collect(Collectors.toSet()));

            if (permissions.size() != permissionIds.size()) {
                throw new AppException(ErrorCode.PERMISSION_NOT_EXISTED);
            }

            Set<Permission> existingPermissions = role.getPermissions();
            Set<Permission> duplicatePermissions = permissions.stream()
                    .filter(existingPermissions::contains)
                    .collect(Collectors.toSet());

            if (!duplicatePermissions.isEmpty()) {
                throw new AppException(ErrorCode.PERMISSION_EXISTED);
            }

            role.getPermissions().addAll(permissions);
            role = roleRepository.save(role);
            return roleMapper.toRoleResponse(role);
        } catch (Exception exception) {
            throw new AppException(ErrorCode.PERMISSION_NOT_EXISTED);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public RoleResponse removePermissionFromRole(Long roleId, Long permissionId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        var permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));
        if (role.getPermissions().contains(permission)) {
            role.getPermissions().remove(permission);
            role = roleRepository.save(role);
            return roleMapper.toRoleResponse(role);
        } else {
            throw new AppException(ErrorCode.PERMISSION_NOT_EXISTED);
        }
    }
}
