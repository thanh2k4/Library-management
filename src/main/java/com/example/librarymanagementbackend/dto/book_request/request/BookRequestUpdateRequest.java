package com.example.librarymanagementbackend.dto.book_request.request;

import com.example.librarymanagementbackend.constants.BookRequestStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequestUpdateRequest {
    @NotNull(message = "Book Loan ID is required")
    Long id;
    BookRequestStatus status;
}
