package com.example.librarymanagementbackend.dto.book_loan.request;

import com.example.librarymanagementbackend.constants.BookLoanStatus;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookLoanCreationRequest {
    Long bookCopyId;
    Long userId;
    Date loanDate;
    int numberOfDaysLoan;
    @NotNull(message = "Status is required")
    BookLoanStatus status;
}