package com.example.librarymanagementbackend.controller;

import com.example.librarymanagementbackend.constants.BookLoanStatus;
import com.example.librarymanagementbackend.dto.base.response.ApiResponse;
import com.example.librarymanagementbackend.dto.base.response.BaseGetAllResponse;
import com.example.librarymanagementbackend.dto.book_loan.request.*;
import com.example.librarymanagementbackend.dto.book_loan.response.BookLoanResponse;

import com.example.librarymanagementbackend.service.BookLoanService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book-loans")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookLoanController {
    BookLoanService bookLoanService;

    @PostMapping("/Create")
    public ApiResponse<BookLoanResponse> createBookLoan(@RequestBody BookLoanCreationRequest request) {
        return ApiResponse.<BookLoanResponse>builder()
                .result(bookLoanService.createBookLoan(request))
                .build();
    }

    @GetMapping("/GetAll")
    public ApiResponse<BaseGetAllResponse<BookLoanResponse>> getAllBookLoans(
            @RequestParam(value = "bookTitle", required = false) String bookTitle,
            @RequestParam(value = "status", required = false) BookLoanStatus status,
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "skipCount", defaultValue = "0") Long skipCount,
            @RequestParam(value = "maxResultCount", defaultValue = "10") Long maxResultCount) {

        BookLoanGetAllRequest request = BookLoanGetAllRequest.builder()
                .bookTitle(bookTitle)
                .status(status)
                .userId(userId)
                .skipCount(skipCount)
                .maxResultCount(maxResultCount)
                .build();

        return ApiResponse.<BaseGetAllResponse<BookLoanResponse>>builder()
                .result(bookLoanService.getAllBookLoans(request))
                .build();
    }

    @GetMapping("/GetById")
    public ApiResponse<BookLoanResponse> getBookLoanById(@RequestParam Long id) {
        return ApiResponse.<BookLoanResponse>builder()
                .result(bookLoanService.getBookLoanById(id))
                .build();
    }

    @DeleteMapping("/Delete")
    public ApiResponse<String> deleteBookLoan(@RequestParam Long id) {
        bookLoanService.deleteBookLoan(id);
        return ApiResponse.<String>builder()
                .result("Book loan deleted successfully")
                .build();
    }

    @PutMapping("/Update")
    public ApiResponse<BookLoanResponse> updateBookLoan(@RequestBody BookLoanUpdateRequest request) {
        return ApiResponse.<BookLoanResponse>builder()
                .result(bookLoanService.updateBookLoan(request))
                .build();
    }

    @PostMapping("/RequestBorrow")
    public ApiResponse<BookLoanResponse> requestBorrow(@Valid @RequestBody BookLoanRequestBorrowRequest request) {
        return ApiResponse.<BookLoanResponse>builder()
                .result(bookLoanService.requestBorrow(request))
                .build();
    }

    @PutMapping("/SetBorrowed")
    public ApiResponse<BookLoanResponse> setBookLoanBorrowed(@RequestBody BookLoanSetBorrowedRequest request) {
        return ApiResponse.<BookLoanResponse>builder()
                .result(bookLoanService.setBookLoanBorrowed(request))
                .build();
    }

    @PutMapping("/RequestReturn")
    public ApiResponse<BookLoanResponse> requestReturn(@RequestBody BookLoanRequestReturnRequest request) {
        return ApiResponse.<BookLoanResponse>builder()
                .result(bookLoanService.requestReturn(request))
                .build();
    }

    @PutMapping("/AcceptReturn")
    public ApiResponse<BookLoanResponse> acceptReturn(@RequestBody BookLoanAcceptReturnRequest request) {
        return ApiResponse.<BookLoanResponse>builder()
                .result(bookLoanService.acceptReturn(request))
                .build();
    }

    @PutMapping("/SetNonreturnable")
    public ApiResponse<BookLoanResponse> setBookLoanNonreturnable(
            @RequestBody BookLoanSetNonreturnableRequest request) {
        return ApiResponse.<BookLoanResponse>builder()
                .result(bookLoanService.setBookLoanNonreturnable(request))
                .build();
    }

    @PutMapping("/RejectBorrow")
    public ResponseEntity<BookLoanResponse> rejectBorrow(@RequestBody BookLoanRejectBorrowRequest request) {
        BookLoanResponse response = bookLoanService.rejectBorrow(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/RejectReturn")
    public ResponseEntity<BookLoanResponse> rejectReturn(@RequestBody BookLoanRejectReturnRequest request) {
        BookLoanResponse response = bookLoanService.rejectReturn(request);
        return ResponseEntity.ok(response);
    }
}