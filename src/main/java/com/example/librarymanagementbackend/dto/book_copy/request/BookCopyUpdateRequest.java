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
public class BookCopyUpdateRequest {
    @NotNull(message = "ID is mandatory")
    Long id;

    @NotNull(message = "Status is mandatory")
    BookCopyStatus status;
}