package com.example.librarymanagementbackend.service;

import com.example.librarymanagementbackend.constants.BookCopyStatus;
import com.example.librarymanagementbackend.dto.base.response.BaseGetAllResponse;
import com.example.librarymanagementbackend.dto.book_copy.request.BookCopyCreateManyRequest;
import com.example.librarymanagementbackend.dto.book_copy.request.BookCopyCreationRequest;
import com.example.librarymanagementbackend.dto.book_copy.request.BookCopyGetAllRequest;
import com.example.librarymanagementbackend.dto.book_copy.request.BookCopyUpdateRequest;
import com.example.librarymanagementbackend.dto.book_copy.response.BookCopyResponse;
import com.example.librarymanagementbackend.entity.Book;
import com.example.librarymanagementbackend.entity.BookCopy;
import com.example.librarymanagementbackend.exception.AppException;
import com.example.librarymanagementbackend.exception.ErrorCode;
import com.example.librarymanagementbackend.mapper.BookCopyMapper;
import com.example.librarymanagementbackend.repository.BookCopyRepository;
import com.example.librarymanagementbackend.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookCopyService {
    private final BookCopyRepository bookCopyRepository;
    private final BookRepository bookRepository;
    private final BookCopyMapper bookCopyMapper;

    public BookCopy createBookCopy(BookCopyCreationRequest request) {
        BookCopy bookCopy = bookCopyMapper.toBookCopy(request);
        bookCopy.setBook(bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_EXISTED)));
        return bookCopyRepository.save(bookCopy);
    }

    public List<BookCopyResponse> createManyBookCopies(BookCopyCreateManyRequest request) {
        Book book = bookRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_EXISTED));

        List<BookCopy> bookCopies = new ArrayList<>();

        for (int i = 0; i < request.getNumber(); i++) {
            BookCopy bookCopy = new BookCopy();
            bookCopy.setBook(book); // Gắn Book từ DB
            bookCopy.setStatus(BookCopyStatus.AVAILABLE);
            bookCopies.add(bookCopy);
        }

        List<BookCopy> savedBookCopies = bookCopyRepository.saveAll(bookCopies);
        return savedBookCopies.stream()
                .map(bookCopyMapper::toBookCopyResponse)
                .collect(Collectors.toList());
    }

    public BaseGetAllResponse<BookCopyResponse> getAllBookCopies(BookCopyGetAllRequest request) {
        Long skipCount = request.getSkipCount() != null ? request.getSkipCount() : 0;
        Long maxResultCount = request.getMaxResultCount() != null ? request.getMaxResultCount() : 10;
        Long bookId = (request.getBookId() == null) ? null : request.getBookId();
        String bookTitle = (request.getBookTitle() == null) ? null : request.getBookTitle();
        BookCopyStatus status = request.getStatus();

        List<BookCopyResponse> bookCopyResponseList = bookCopyRepository.findAllByFilters(bookId, bookTitle, status)
                .stream()
                .skip(skipCount)
                .limit(maxResultCount)
                .map(bookCopyMapper::toBookCopyResponse)
                .collect(Collectors.toList());

        return BaseGetAllResponse.<BookCopyResponse>builder()
                .data(bookCopyResponseList)
                .totalRecords(bookCopyRepository.countByFilters(bookId, bookTitle, status))
                .build();
    }

    public BookCopyResponse getBookCopyById(Long id) {
        BookCopy bookCopy = bookCopyRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_COPY_NOT_EXISTED));
        return bookCopyMapper.toBookCopyResponse(bookCopy);
    }

    public BookCopyResponse updateBookCopy(BookCopyUpdateRequest request) {
        BookCopy bookCopy = bookCopyRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_COPY_NOT_EXISTED));
        bookCopyMapper.updateBookCopy(bookCopy, request);
        bookCopy = bookCopyRepository.save(bookCopy);
        return bookCopyMapper.toBookCopyResponse(bookCopy);
    }

    public void deleteBookCopy(Long id) {
        if (!bookCopyRepository.existsById(id)) {
            throw new AppException(ErrorCode.BOOK_COPY_NOT_EXISTED);
        }
        bookCopyRepository.deleteById(id);
    }
}