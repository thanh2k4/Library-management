package com.example.librarymanagementbackend.dto.book_loan.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookLoanRequestBorrowRequest {
    @NotNull(message = "Book ID is required")
    Long bookId;
    @NotNull(message = "User ID is required")
    Long userId;
    @NotNull(message = "Loan Date is required")
    Date loanDate;
    @NotNull(message = "Number of Days Loan is required")
    int numberOfDaysLoan;
}