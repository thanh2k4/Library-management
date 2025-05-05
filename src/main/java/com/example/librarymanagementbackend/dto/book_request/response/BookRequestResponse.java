package com.example.librarymanagementbackend.dto.book_request.response;

import com.example.librarymanagementbackend.constants.BookRequestStatus;
import com.example.librarymanagementbackend.constants.BookRequestType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequestResponse {
    Long id;
    Long bookCopyId;
    String bookTitle;
    Long userId;
    String username;
    BookRequestType type;
    BookRequestStatus status;
    Date createdAt;
    Date updatedAt;
}