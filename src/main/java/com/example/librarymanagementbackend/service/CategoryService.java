package com.example.librarymanagementbackend.service;

import com.example.librarymanagementbackend.dto.base.response.BaseGetAllResponse;
import com.example.librarymanagementbackend.dto.category.request.CategoryCreationRequest;
import com.example.librarymanagementbackend.dto.category.request.CategoryGetAllRequest;
import com.example.librarymanagementbackend.dto.category.request.CategoryUpdateRequest;
import com.example.librarymanagementbackend.dto.category.response.CategoryResponse;
import com.example.librarymanagementbackend.entity.Category;
import com.example.librarymanagementbackend.exception.AppException;
import com.example.librarymanagementbackend.exception.ErrorCode;
import com.example.librarymanagementbackend.mapper.CategoryMapper;
import com.example.librarymanagementbackend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@Slf4j
public class CategoryService {
    private CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryResponse createCategory(CategoryCreationRequest request) {
        Category category = categoryMapper.toCategory(request);
        try {
            category = categoryRepository.save(category);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
        return categoryMapper.toCategoryResponse(category);
    }

    public BaseGetAllResponse<CategoryResponse> getAllCategories(CategoryGetAllRequest request) {
        Long skipCount = request.getSkipCount() != null ? request.getSkipCount() : 0;
        Long maxResultCount = request.getMaxResultCount() != null ? request.getMaxResultCount() : 10;
        String name = (request.getName() == null || request.getName().isEmpty()) ? null : request.getName();

        List<CategoryResponse> categoryResponseList = categoryRepository.findAllByFilters(name)
                .stream()
                .skip(skipCount)
                .limit(maxResultCount)
                .map(categoryMapper::toCategoryResponse)
                .collect(Collectors.toList());

        return BaseGetAllResponse.<CategoryResponse>builder()
                .data(categoryResponseList)
                .totalRecords(categoryRepository.countByFilters(name))
                .build();
    }

    public CategoryResponse updateCategory(CategoryUpdateRequest request) {
        Category category = categoryRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        categoryMapper.updateCategory(category, request);
        category = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new AppException(ErrorCode.CATEGORY_NOT_EXISTED);
        }
        categoryRepository.deleteById(id);
    }

    public CategoryResponse getCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        return categoryMapper.toCategoryResponse(category);
    }
}