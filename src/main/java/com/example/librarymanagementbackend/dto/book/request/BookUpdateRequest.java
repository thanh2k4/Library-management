package com.example.librarymanagementbackend.dto.book.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookUpdateRequest {
    Long id;

    @NotBlank(message = "Title is mandatory")
    String title;

    @NotNull(message = "Price is mandatory")
    Double price;

    String description;

    @NotNull(message = "Publisher ID is mandatory")
    Long publisherId;

    @NotNull(message = "Author IDs are mandatory")
    Set<Long> authorIds;

    @NotNull(message = "Category IDs are mandatory")
    Set<Long> categoryIds;
}