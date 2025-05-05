package com.example.librarymanagementbackend.mapper;

import com.example.librarymanagementbackend.dto.book_loan.request.BookLoanCreationRequest;
import com.example.librarymanagementbackend.dto.book_loan.request.BookLoanUpdateRequest;
import com.example.librarymanagementbackend.dto.book_loan.response.BookLoanResponse;
import com.example.librarymanagementbackend.entity.BookLoan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookLoanMapper {
    @Mapping(target = "bookCopy.id", source = "bookCopyId")
    @Mapping(target = "status", source = "status")
    BookLoan toBookLoan(BookLoanCreationRequest request);

    void updateBookLoan(@MappingTarget BookLoan bookLoan, BookLoanUpdateRequest request);

    @Mapping(source = "bookCopy.book.title", target = "bookTitle")
    @Mapping(source = "bookCopy.id", target = "bookCopyId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "username")
    @Mapping(source = "loanDate", target = "loanDate")
    @Mapping(source = "returnDate", target = "returnDate")
    @Mapping(source = "actualReturnDate", target = "actualReturnDate")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "currentBookRequestId", target = "bookRequestId")
    BookLoanResponse toBookLoanResponse(BookLoan bookLoan);
}