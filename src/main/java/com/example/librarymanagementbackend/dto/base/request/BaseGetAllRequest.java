package com.example.librarymanagementbackend.dto.base.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseGetAllRequest {
    protected Long skipCount = 0L;
    protected Long maxResultCount = 10L;
}
