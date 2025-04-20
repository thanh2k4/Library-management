package com.example.librarymanagementbackend.controller;

import com.example.librarymanagementbackend.dto.base.response.ApiResponse;
import com.example.librarymanagementbackend.dto.base.response.BaseGetAllResponse;
import com.example.librarymanagementbackend.dto.permission.request.PermissionGetAllRequest;
import com.example.librarymanagementbackend.dto.permission.request.PermissionRequest;
import com.example.librarymanagementbackend.dto.permission.request.PermissionUpdateRequest;
import com.example.librarymanagementbackend.dto.permission.response.PermissionResponse;
import com.example.librarymanagementbackend.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionController {
    PermissionService permissionService;

    @PostMapping("/Create")
    ApiResponse<PermissionResponse> createUser(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder().result(permissionService.createPermission(request)).build();
    }

    @GetMapping("/GetAll")
    ApiResponse<BaseGetAllResponse<PermissionResponse>> getAllPermission(
            @RequestParam(value = "skipCount", defaultValue = "0") Long skipCount,
            @RequestParam(value = "maxResultCount", defaultValue = "10") Long maxResultCount) {
        PermissionGetAllRequest request = new PermissionGetAllRequest();
        request.setSkipCount(skipCount);
        request.setMaxResultCount(maxResultCount);
        return ApiResponse.<BaseGetAllResponse<PermissionResponse>>builder()
                .result(permissionService.getAllPermission(request)).build();
    }

    @GetMapping("/GetById")
    ApiResponse<PermissionResponse> getPermissionById(@RequestParam Long id) {
        return ApiResponse.<PermissionResponse>builder().result(permissionService.getPermissionById(id)).build();
    }

    @PutMapping("/Update")
    ApiResponse<PermissionResponse> updatePermission(@RequestBody PermissionUpdateRequest request) {
        return ApiResponse.<PermissionResponse>builder().result(permissionService.updatePermission(request)).build();
    }

    @DeleteMapping("/Delete")
    ApiResponse<String> deletePermission(@RequestParam Long id) {
        permissionService.deletePermission(id);
        return ApiResponse.<String>builder().result("Permission deleted successfully").build();
    }

}
