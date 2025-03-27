package com.example.librarymanagementbackend.dto.book_copy.request;

import com.example.librarymanagementbackend.constants.BookCopyStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookCopyGetAllRequest {
    Long bookId;
    String bookTitle;
    BookCopyStatus status;
    Long skipCount;
    Long maxResultCount;
}