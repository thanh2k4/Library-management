package com.example.librarymanagementbackend.dto.fine.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FineResponse {
    Long id;
    Long userId;
    String username;
    Long bookLoanId;
    String bookTitle;
    Double amount;
    Date createdAt;
    Date updatedAt;
}