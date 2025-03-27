package com.example.librarymanagementbackend.dto.book_copy.response;

import com.example.librarymanagementbackend.constants.BookCopyStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookCopyResponse {
    Long id;
    Long bookId;
    String bookTitle;
    BookCopyStatus status;
    Date createdAt;
    Date updatedAt;
}