package com.example.librarymanagementbackend.dto.book_loan.response;

import com.example.librarymanagementbackend.constants.BookCopyStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookLoanResponse {
    Long id;
    Long bookCopyId;
    Long userId;
    String userName;
    Date loanDate;
    Date returnDate;
    Date actualReturnDate;
    BookCopyStatus status;
    String bookTitle;
    Long bookRequestId;
}