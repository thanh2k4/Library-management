package com.example.librarymanagementbackend.controller;

import com.example.librarymanagementbackend.dto.base.response.ApiResponse;
import com.example.librarymanagementbackend.dto.base.response.BaseGetAllResponse;
import com.example.librarymanagementbackend.dto.category.request.CategoryCreationRequest;
import com.example.librarymanagementbackend.dto.category.request.CategoryGetAllRequest;
import com.example.librarymanagementbackend.dto.category.request.CategoryUpdateRequest;
import com.example.librarymanagementbackend.dto.category.response.CategoryResponse;
import com.example.librarymanagementbackend.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryController {
    CategoryService categoryService;

    @PostMapping("/Create")
    ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryCreationRequest request) {
        return ApiResponse.<CategoryResponse>builder().result(categoryService.createCategory(request)).build();
    }

    @GetMapping("/GetAll")
    ApiResponse<BaseGetAllResponse<CategoryResponse>> getAllCategories(
            @RequestParam(value = "skipCount", defaultValue = "0") Long skipCount,
            @RequestParam(value = "maxResultCount", defaultValue = "10") Long maxResultCount,
            @RequestParam(value = "name", required = false) String name) {

        CategoryGetAllRequest request = new CategoryGetAllRequest();
        request.setSkipCount(skipCount);
        request.setMaxResultCount(maxResultCount);
        request.setName(name);

        return ApiResponse.<BaseGetAllResponse<CategoryResponse>>builder()
                .result(categoryService.getAllCategories(request))
                .build();
    }

    @GetMapping("/GetById")
    ApiResponse<CategoryResponse> getCategoryById(@RequestParam Long id) {
        return ApiResponse.<CategoryResponse>builder().result(categoryService.getCategory(id)).build();
    }

    @DeleteMapping("/Delete")
    ApiResponse<String> deleteCategory(@RequestParam Long id) {
        categoryService.deleteCategory(id);
        return ApiResponse.<String>builder().result("Category deleted successfully").build();
    }

    @PutMapping("/Update")
    ApiResponse<CategoryResponse> updateCategory(@RequestBody CategoryUpdateRequest request) {
        return ApiResponse.<CategoryResponse>builder().result(categoryService.updateCategory(request)).build();
    }
}