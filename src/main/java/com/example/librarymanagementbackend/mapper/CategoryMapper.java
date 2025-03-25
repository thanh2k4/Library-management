package com.example.librarymanagementbackend.mapper;

import com.example.librarymanagementbackend.dto.category.request.CategoryCreationRequest;
import com.example.librarymanagementbackend.dto.category.request.CategoryUpdateRequest;
import com.example.librarymanagementbackend.dto.category.response.CategoryResponse;
import com.example.librarymanagementbackend.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryCreationRequest request);

    CategoryResponse toCategoryResponse(Category category);

    void updateCategory(@MappingTarget Category category, CategoryUpdateRequest request);
}