package com.example.librarymanagementbackend.dto.book_request.request;

import com.example.librarymanagementbackend.constants.BookRequestStatus;
import com.example.librarymanagementbackend.constants.BookRequestType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequestCreationRequest {
    Long BookLoanId;
    BookRequestStatus status;
    BookRequestType type;
}
