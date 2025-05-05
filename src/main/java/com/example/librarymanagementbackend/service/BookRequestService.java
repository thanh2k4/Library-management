package com.example.librarymanagementbackend.service;

import com.example.librarymanagementbackend.constants.BookRequestStatus;
import com.example.librarymanagementbackend.constants.BookRequestType;
import com.example.librarymanagementbackend.dto.base.response.BaseGetAllResponse;
import com.example.librarymanagementbackend.dto.book_request.request.BookRequestCreationRequest;
import com.example.librarymanagementbackend.dto.book_request.request.BookRequestGetAllRequest;
import com.example.librarymanagementbackend.dto.book_request.request.BookRequestUpdateRequest;
import com.example.librarymanagementbackend.dto.book_request.response.BookRequestResponse;
import com.example.librarymanagementbackend.entity.BookLoan;
import com.example.librarymanagementbackend.entity.BookRequest;
import com.example.librarymanagementbackend.exception.AppException;
import com.example.librarymanagementbackend.exception.ErrorCode;
import com.example.librarymanagementbackend.mapper.BookRequestMapper;
import com.example.librarymanagementbackend.repository.BookLoanRepository;
import com.example.librarymanagementbackend.repository.BookRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class BookRequestService {

        BookRequestRepository bookRequestRepository;
        BookLoanRepository bookLoanRepository;
        BookRequestMapper bookRequestMapper;

        public BookRequestResponse createBookRequest(BookRequestCreationRequest request) {

                BookLoan bookLoan = bookLoanRepository.findById(request.getBookLoanId())
                                .orElseThrow(() -> new AppException(ErrorCode.BOOK_LOAN_NOT_EXISTED));

                BookRequest bookRequest = bookRequestMapper.toBookRequest(request);
                bookRequest.setBookLoan(bookLoan);

                bookRequest = bookRequestRepository.save(bookRequest);
                return bookRequestMapper.toBookRequestResponse(bookRequest);
        }

        public void deleteBookRequest(Long id) {
                BookRequest bookRequest = bookRequestRepository.findById(id)
                                .orElseThrow(() -> new AppException(ErrorCode.BOOK_REQUEST_NOT_EXISTED));

                bookRequest.setBookLoan(null);
                bookRequestRepository.delete(bookRequest);
        }

        public BookRequestResponse updateBookRequest(BookRequestUpdateRequest request) {
                BookRequest bookRequest = bookRequestRepository.findById(request.getId())
                                .orElseThrow(() -> new AppException(ErrorCode.BOOK_REQUEST_NOT_EXISTED));

                bookRequestMapper.updateBookRequest(bookRequest, request);

                bookRequestRepository.save(bookRequest);
                return bookRequestMapper.toBookRequestResponse(bookRequest);

        }

        public BookRequestResponse getBookRequestById(Long id) {
                BookRequest bookRequest = bookRequestRepository.findById(id)
                                .orElseThrow(() -> new AppException(ErrorCode.BOOK_REQUEST_NOT_EXISTED));

                return bookRequestMapper.toBookRequestResponse(bookRequest);
        }

        public BaseGetAllResponse<BookRequestResponse> getAllBookRequests(BookRequestGetAllRequest request) {
                Long skipCount = request.getSkipCount() != null ? request.getSkipCount() : 0;
                Long maxResultCount = request.getMaxResultCount() != null ? request.getMaxResultCount() : 10;
                String bookTitle = (request.getBookTitle() == null || request.getBookTitle().isEmpty()) ? null
                                : request.getBookTitle();
                Long userId = (request.getUserId() == null) ? null : request.getUserId();
                String username = (request.getUsername() == null || request.getUsername().isEmpty()) ? null
                                : request.getUsername();
                BookRequestStatus status = request.getStatus();
                BookRequestType type = request.getType();

                List<BookRequestResponse> bookRequestResponseList = bookRequestRepository
                                .findAllByFilters(bookTitle, status, userId, type, username)
                                .stream()
                                .skip(skipCount)
                                .limit(maxResultCount)
                                .map(bookRequestMapper::toBookRequestResponse)
                                .collect(Collectors.toList());

                return BaseGetAllResponse.<BookRequestResponse>builder()
                                .data(bookRequestResponseList)
                                .totalRecords(bookRequestRepository.countByFilters(bookTitle, status, userId, type,
                                                username))
                                .build();
        }

}
