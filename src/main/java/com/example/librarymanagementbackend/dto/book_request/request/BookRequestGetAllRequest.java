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
public class BookRequestGetAllRequest {
    String bookTitle;
    Long userId;
    String username;
    BookRequestStatus status;
    BookRequestType type;
    Long skipCount;
    Long maxResultCount;
}