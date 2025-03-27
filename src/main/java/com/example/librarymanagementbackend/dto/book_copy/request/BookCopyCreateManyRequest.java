package com.example.librarymanagementbackend.dto.book_copy.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookCopyCreateManyRequest {
    @NotNull(message = "Book ID is mandatory")
    Long id;
    @NotNull(message = "number is mandatory")
    @Max(value = 10, message = "The maximum number of book copies is 10")
    Long number;
}