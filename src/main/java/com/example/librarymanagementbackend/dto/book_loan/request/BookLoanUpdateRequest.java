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
public class BookLoanUpdateRequest {
    @NotNull(message = "Book Loan ID is required")
    Long id;
    Date loanDate;
    Date returnDate;
    Date actualReturnDate;
}