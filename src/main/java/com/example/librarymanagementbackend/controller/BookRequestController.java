package com.example.librarymanagementbackend.controller;

import com.example.librarymanagementbackend.constants.BookRequestStatus;
import com.example.librarymanagementbackend.constants.BookRequestType;
import com.example.librarymanagementbackend.dto.base.response.ApiResponse;
import com.example.librarymanagementbackend.dto.base.response.BaseGetAllResponse;
import com.example.librarymanagementbackend.dto.book_request.request.BookRequestCreationRequest;
import com.example.librarymanagementbackend.dto.book_request.request.BookRequestGetAllRequest;
import com.example.librarymanagementbackend.dto.book_request.request.BookRequestUpdateRequest;
import com.example.librarymanagementbackend.dto.book_request.response.BookRequestResponse;
import com.example.librarymanagementbackend.service.BookRequestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book-requests")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookRequestController {
        BookRequestService bookRequestService;

        @PostMapping("/Create")
        public ApiResponse<BookRequestResponse> createBookRequest(@RequestBody BookRequestCreationRequest request) {
                return ApiResponse.<BookRequestResponse>builder()
                                .result(bookRequestService.createBookRequest(request))
                                .build();
        }

        @GetMapping("/GetById")
        public ApiResponse<BookRequestResponse> getBookRequestById(@RequestParam Long id) {
                return ApiResponse.<BookRequestResponse>builder()
                                .result(bookRequestService.getBookRequestById(id))
                                .build();
        }

        @GetMapping("/GetAll")
        public ApiResponse<BaseGetAllResponse<BookRequestResponse>> getAllBookLoans(
                        @RequestParam(value = "bookTitle", required = false) String bookTitle,
                        @RequestParam(value = "status", required = false) BookRequestStatus status,
                        @RequestParam(value = "userId", required = false) Long userId,
                        @RequestParam(value = "username", required = false) String username,
                        @RequestParam(value = "type", required = false) BookRequestType type,
                        @RequestParam(value = "skipCount", defaultValue = "0") Long skipCount,
                        @RequestParam(value = "maxResultCount", defaultValue = "10") Long maxResultCount) {

                BookRequestGetAllRequest request = BookRequestGetAllRequest.builder()
                                .bookTitle(bookTitle)
                                .userId(userId)
                                .username(username)
                                .type(type)
                                .status(status)
                                .skipCount(skipCount)
                                .maxResultCount(maxResultCount)
                                .build();

                return ApiResponse.<BaseGetAllResponse<BookRequestResponse>>builder()
                                .result(bookRequestService.getAllBookRequests(request))
                                .build();
        }

        @PutMapping("/Update")
        public ApiResponse<BookRequestResponse> updateBookRequest(@RequestBody BookRequestUpdateRequest request) {
                return ApiResponse.<BookRequestResponse>builder()
                                .result(bookRequestService.updateBookRequest(request))
                                .build();
        }

        @DeleteMapping("/Delete")
        public ApiResponse<String> deleteBookRequest(@RequestParam Long id) {
                bookRequestService.deleteBookRequest(id);
                return ApiResponse.<String>builder()
                                .result("Book request deleted successfully")
                                .build();
        }
}
