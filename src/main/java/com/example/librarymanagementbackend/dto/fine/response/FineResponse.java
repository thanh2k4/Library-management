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
    String userName;
    Long bookLoanId;
    Long bookTitle;
    Double amount;
    Date createdAt;
    Date updatedAt;
}