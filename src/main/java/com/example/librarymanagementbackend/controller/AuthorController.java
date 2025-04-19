package com.example.librarymanagementbackend.controller;

import com.example.librarymanagementbackend.dto.base.response.ApiResponse;
import com.example.librarymanagementbackend.dto.author.request.AuthorCreationRequest;
import com.example.librarymanagementbackend.dto.author.request.AuthorGetAllRequest;
import com.example.librarymanagementbackend.dto.author.request.AuthorUpdateRequest;
import com.example.librarymanagementbackend.dto.author.response.AuthorResponse;
import com.example.librarymanagementbackend.dto.base.response.BaseGetAllResponse;
import com.example.librarymanagementbackend.service.AuthorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthorController {
        AuthorService authorService;

        @PostMapping("/Create")
        ApiResponse<AuthorResponse> createAuthor(@RequestBody AuthorCreationRequest request) {
                return ApiResponse.<AuthorResponse>builder().result(authorService.createAuthor(request)).build();
        }

        @GetMapping("/GetAll")
        ApiResponse<BaseGetAllResponse<AuthorResponse>> getAllAuthors(
                        @RequestParam(value = "skipCount", defaultValue = "0") Long skipCount,
                        @RequestParam(value = "maxResultCount", defaultValue = "10") Long maxResultCount,
                        @RequestParam(value = "name", required = false) String name) {

                AuthorGetAllRequest request = AuthorGetAllRequest.builder()
                                .name(name)
                                .build();
                request.setSkipCount(skipCount);
                request.setMaxResultCount(maxResultCount);

                return ApiResponse.<BaseGetAllResponse<AuthorResponse>>builder()
                                .result(authorService.getAllAuthors(request))
                                .build();
        }

        @GetMapping("/GetById")
        ApiResponse<AuthorResponse> getAuthorById(@RequestParam Long id) {
                return ApiResponse.<AuthorResponse>builder().result(authorService.getAuthor(id)).build();
        }

        @DeleteMapping("/Delete")
        ApiResponse<String> deleteAuthor(@RequestParam Long id) {
                authorService.deleteAuthor(id);
                return ApiResponse.<String>builder().result("Author deleted successfully").build();
        }

        @PutMapping("/Update")
        ApiResponse<AuthorResponse> updateAuthor(@RequestBody AuthorUpdateRequest request) {
                return ApiResponse.<AuthorResponse>builder().result(authorService.updateAuthor(request)).build();
        }
}