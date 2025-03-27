package com.example.librarymanagementbackend.dto.fine.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FineUpdateRequest {
    Long id;
    Double amount;
}