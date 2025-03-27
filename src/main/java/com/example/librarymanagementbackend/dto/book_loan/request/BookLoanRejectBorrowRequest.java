package com.example.librarymanagementbackend.dto.book_loan.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookLoanRejectBorrowRequest {
    Long bookLoanId;
}