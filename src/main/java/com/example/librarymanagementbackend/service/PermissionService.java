package com.example.librarymanagementbackend.service;

import com.example.librarymanagementbackend.dto.base.response.BaseGetAllResponse;
import com.example.librarymanagementbackend.dto.permission.request.PermissionGetAllRequest;
import com.example.librarymanagementbackend.dto.permission.request.PermissionRequest;
import com.example.librarymanagementbackend.dto.permission.request.PermissionUpdateRequest;
import com.example.librarymanagementbackend.dto.permission.response.PermissionResponse;
import com.example.librarymanagementbackend.entity.Permission;
import com.example.librarymanagementbackend.exception.AppException;
import com.example.librarymanagementbackend.exception.ErrorCode;
import com.example.librarymanagementbackend.mapper.PermissionMapper;
import com.example.librarymanagementbackend.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@Slf4j
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse createPermission(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        try {
            permission = permissionRepository.save(permission);
        } catch (Exception exception) {
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }
        return permissionMapper.toPermissionResponse(permission);
    }

    public BaseGetAllResponse<PermissionResponse> getAllPermission(PermissionGetAllRequest request) {
        Long skipCount = request.getSkipCount() != null ? request.getSkipCount() : 0L;
        Long maxResultCount = request.getMaxResultCount() != null ? request.getMaxResultCount() : 10L;

        List<PermissionResponse> permissionList = permissionRepository.findAll()
                .stream()
                .skip(skipCount)
                .limit(maxResultCount)
                .map(permissionMapper::toPermissionResponse)
                .toList();

        return BaseGetAllResponse.<PermissionResponse>builder()
                .data(permissionList)
                .totalRecords(permissionRepository.count())
                .build();
    }

    public PermissionResponse getPermissionById(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));
        return permissionMapper.toPermissionResponse(permission);
    }

    public PermissionResponse updatePermission(PermissionUpdateRequest request) {
        Permission permission = permissionRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));
        permissionMapper.updatePermission(permission, request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public void deletePermission(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));
        permissionRepository.delete(permission);
    }
}
