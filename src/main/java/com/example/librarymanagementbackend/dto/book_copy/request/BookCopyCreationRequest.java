package com.example.librarymanagementbackend.dto.book_copy.request;

import com.example.librarymanagementbackend.constants.BookCopyStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookCopyCreationRequest {
    @NotNull(message = "Book ID is mandatory")
    Long bookId;
    @NotNull(message = "status is mandatory")
    BookCopyStatus status = BookCopyStatus.AVAILABLE;
}