package com.example.librarymanagementbackend.dto.book_loan.request;

import com.example.librarymanagementbackend.constants.BookCopyStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookLoanGetAllRequest {
    String bookTitle;
    BookCopyStatus status;
    Long userId;
    Long skipCount;
    Long maxResultCount;
}