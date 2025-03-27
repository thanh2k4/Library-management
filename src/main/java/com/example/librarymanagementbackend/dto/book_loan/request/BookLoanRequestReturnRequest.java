package com.example.librarymanagementbackend.dto.book_loan.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookLoanRequestReturnRequest {
    Long bookLoanId;
    Date actualReturnDate;
}