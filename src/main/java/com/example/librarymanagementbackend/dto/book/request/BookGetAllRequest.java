package com.example.librarymanagementbackend.dto.book.request;

import com.example.librarymanagementbackend.dto.base.request.BaseGetAllRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookGetAllRequest extends BaseGetAllRequest {
    String title;
    Long publisherId;
    Long authorId;
    Long categoryId;
}