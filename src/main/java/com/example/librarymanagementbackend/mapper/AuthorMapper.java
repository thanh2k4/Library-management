package com.example.librarymanagementbackend.mapper;

import com.example.librarymanagementbackend.dto.author.request.AuthorCreationRequest;
import com.example.librarymanagementbackend.dto.author.request.AuthorUpdateRequest;
import com.example.librarymanagementbackend.dto.author.response.AuthorResponse;
import com.example.librarymanagementbackend.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toAuthor(AuthorCreationRequest request);

    AuthorResponse toAuthorResponse(Author author);

    void updateAuthor(@MappingTarget Author author, AuthorUpdateRequest request);
}
