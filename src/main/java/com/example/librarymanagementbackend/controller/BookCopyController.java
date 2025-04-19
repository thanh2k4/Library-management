package com.example.librarymanagementbackend.controller;

import com.example.librarymanagementbackend.constants.BookCopyStatus;
import com.example.librarymanagementbackend.dto.base.response.ApiResponse;
import com.example.librarymanagementbackend.dto.base.response.BaseGetAllResponse;
import com.example.librarymanagementbackend.dto.book_copy.request.BookCopyCreateManyRequest;
import com.example.librarymanagementbackend.dto.book_copy.request.BookCopyCreationRequest;
import com.example.librarymanagementbackend.dto.book_copy.request.BookCopyGetAllRequest;
import com.example.librarymanagementbackend.dto.book_copy.request.BookCopyUpdateRequest;
import com.example.librarymanagementbackend.dto.book_copy.response.BookCopyResponse;
import com.example.librarymanagementbackend.entity.BookCopy;
import com.example.librarymanagementbackend.mapper.BookCopyMapper;
import com.example.librarymanagementbackend.service.BookCopyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-copies")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookCopyController {
        BookCopyService bookCopyService;
        BookCopyMapper bookCopyMapper;

        @PostMapping("/Create")
        ApiResponse<BookCopyResponse> createBookCopy(@RequestBody BookCopyCreationRequest request) {
                BookCopy bookCopy = bookCopyService.createBookCopy(request);
                return ApiResponse.<BookCopyResponse>builder()
                                .result(bookCopyMapper.toBookCopyResponse(bookCopy))
                                .build();
        }

        @PostMapping("/CreateMany")
        ApiResponse<List<BookCopyResponse>> createManyBookCopies(@RequestBody BookCopyCreateManyRequest request) {
                return ApiResponse.<List<BookCopyResponse>>builder()
                                .result(bookCopyService.createManyBookCopies(request))
                                .build();
        }

        @GetMapping("/GetAll")
        ApiResponse<BaseGetAllResponse<BookCopyResponse>> getAllBookCopies(
                        @RequestParam(value = "bookId", required = false) Long bookId,
                        @RequestParam(value = "bookTitle", required = false) String bookTitle,
                        @RequestParam(value = "status", required = false) BookCopyStatus status,
                        @RequestParam(value = "skipCount", defaultValue = "0") Long skipCount,
                        @RequestParam(value = "maxResultCount", defaultValue = "10") Long maxResultCount) {

                BookCopyGetAllRequest request = BookCopyGetAllRequest.builder()
                                .bookId(bookId)
                                .bookTitle(bookTitle)
                                .status(status)
                                .skipCount(skipCount)
                                .maxResultCount(maxResultCount)
                                .build();

                return ApiResponse.<BaseGetAllResponse<BookCopyResponse>>builder()
                                .result(bookCopyService.getAllBookCopies(request))
                                .build();
        }

        @GetMapping("/GetById")
        public ApiResponse<BookCopyResponse> getBookCopyById(@RequestParam Long id) {
                return ApiResponse.<BookCopyResponse>builder()
                                .result(bookCopyService.getBookCopyById(id))
                                .build();
        }

        @PutMapping("/Update")
        ApiResponse<BookCopyResponse> updateBookCopy(@RequestBody BookCopyUpdateRequest request) {
                return ApiResponse.<BookCopyResponse>builder()
                                .result(bookCopyService.updateBookCopy(request))
                                .build();
        }

        @DeleteMapping("/Delete")
        ApiResponse<String> deleteBookCopy(@RequestParam Long id) {
                bookCopyService.deleteBookCopy(id);
                return ApiResponse.<String>builder()
                                .result("Book copy deleted successfully")
                                .build();
        }
}