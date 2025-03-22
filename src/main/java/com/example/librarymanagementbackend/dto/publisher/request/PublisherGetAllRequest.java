package com.example.librarymanagementbackend.dto.publisher.request;

import com.example.librarymanagementbackend.dto.base.request.BaseGetAllRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublisherGetAllRequest extends BaseGetAllRequest {
    String name;
}
