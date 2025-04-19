package com.example.librarymanagementbackend.controller;

import com.example.librarymanagementbackend.dto.base.response.ApiResponse;
import com.example.librarymanagementbackend.dto.base.response.BaseGetAllResponse;
import com.example.librarymanagementbackend.dto.book.request.BookCreationRequest;
import com.example.librarymanagementbackend.dto.book.request.BookGetAllRequest;
import com.example.librarymanagementbackend.dto.book.request.BookUpdateRequest;
import com.example.librarymanagementbackend.dto.book.response.BookResponse;
import com.example.librarymanagementbackend.service.BookService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookController {
    BookService bookService;

    @PostMapping("/Create")
    ApiResponse<BookResponse> createBook(@RequestBody BookCreationRequest request) {
        return ApiResponse.<BookResponse>builder()
                .result(bookService.createBook(request))
                .build();
    }

    @GetMapping("/GetAll")
    ApiResponse<BaseGetAllResponse<BookResponse>> getAllBooks(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "publisherId", required = false) Long publisherId,
            @RequestParam(value = "authorId", required = false) Long authorId,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "skipCount", defaultValue = "0") Long skipCount,
            @RequestParam(value = "maxResultCount", defaultValue = "10") Long maxResultCount) {

        BookGetAllRequest request = new BookGetAllRequest();
        request.setTitle(title);
        request.setPublisherId(publisherId);
        request.setAuthorId(authorId);
        request.setCategoryId(categoryId);
        request.setSkipCount(skipCount);
        request.setMaxResultCount(maxResultCount);

        return ApiResponse.<BaseGetAllResponse<BookResponse>>builder()
                .result(bookService.getAllBooks(request))
                .build();
    }

    @GetMapping("/GetById")
    ApiResponse<BookResponse> getBookById(@RequestParam Long id) {
        return ApiResponse.<BookResponse>builder()
                .result(bookService.getBookById(id))
                .build();
    }

    @DeleteMapping("/Delete")
    ApiResponse<String> deleteBook(@RequestParam Long id) {
        bookService.deleteBook(id);
        return ApiResponse.<String>builder()
                .result("Book deleted successfully")
                .build();
    }

    @PutMapping("/Update")
    ApiResponse<BookResponse> updateBook(@RequestBody BookUpdateRequest request) {
        return ApiResponse.<BookResponse>builder()
                .result(bookService.updateBook(request))
                .build();
    }
}