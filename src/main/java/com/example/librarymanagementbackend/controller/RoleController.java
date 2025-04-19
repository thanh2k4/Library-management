package com.example.librarymanagementbackend.controller;

import com.example.librarymanagementbackend.dto.base.response.ApiResponse;
import com.example.librarymanagementbackend.dto.base.response.BaseGetAllResponse;
import com.example.librarymanagementbackend.dto.role.request.RoleRequest;
import com.example.librarymanagementbackend.dto.role.request.RoleUpdateRequest;
import com.example.librarymanagementbackend.dto.role.response.RoleResponse;
import com.example.librarymanagementbackend.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
    RoleService roleService;

    @PostMapping("/Create")
    ApiResponse<RoleResponse> createUser(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder().result(roleService.createRole(request)).build();
    }

    @GetMapping("/GetAll")
    ApiResponse<BaseGetAllResponse<RoleResponse>> getAllRole() {
        return ApiResponse.<BaseGetAllResponse<RoleResponse>>builder().result(roleService.getAllRole()).build();
    }

    @GetMapping("/GetById")
    ApiResponse<RoleResponse> getRoleById(@RequestParam Long id) {
        return ApiResponse.<RoleResponse>builder().result(roleService.getRoleById(id)).build();
    }

    @PutMapping("/Update")
    ApiResponse<RoleResponse> updateRole(@RequestBody RoleUpdateRequest request) {
        return ApiResponse.<RoleResponse>builder().result(roleService.updateRole(request)).build();
    }

    @DeleteMapping("/Delete")
    ApiResponse<String> deleteRole(@RequestParam Long id) {
        roleService.deleteRole(id);
        return ApiResponse.<String>builder().result("Role deleted successfully").build();
    }

    @PutMapping("/AddPermissions")
    ApiResponse<RoleResponse> addPermissions(@RequestParam Long id, @RequestBody List<Long> request) {
        return ApiResponse.<RoleResponse>builder().result(roleService.addPermissionstoRole(id, request)).build();
    }

    @PutMapping("/RemovePermission")
    ApiResponse<RoleResponse> removePermission(@RequestParam Long id, @RequestParam Long permissionId) {
        return ApiResponse.<RoleResponse>builder().result(roleService.removePermissionFromRole(id, permissionId))
                .build();
    }

}
