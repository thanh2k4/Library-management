package com.example.librarymanagementbackend.dto.publisher.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublisherResponse {
    int id;
    String name;
}
