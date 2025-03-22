package com.example.librarymanagementbackend.dto.category.request;

import com.example.librarymanagementbackend.dto.base.request.BaseGetAllRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryGetAllRequest extends BaseGetAllRequest {
    String name;
}