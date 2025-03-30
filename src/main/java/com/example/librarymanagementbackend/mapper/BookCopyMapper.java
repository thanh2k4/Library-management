package com.example.librarymanagementbackend.mapper;

import com.example.librarymanagementbackend.constants.BookCopyStatus;
import com.example.librarymanagementbackend.dto.book_copy.request.BookCopyCreationRequest;
import com.example.librarymanagementbackend.dto.book_copy.request.BookCopyUpdateRequest;
import com.example.librarymanagementbackend.dto.book_copy.response.BookCopyResponse;
import com.example.librarymanagementbackend.entity.BookCopy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BookCopyMapper {
    @Mapping(target = "book.id", source = "bookId")
    @Mapping(target = "status", source = "status", qualifiedByName = "defaultStatus")
    BookCopy toBookCopy(BookCopyCreationRequest request);

    @Named("defaultStatus")
    default BookCopyStatus defaultStatus(BookCopyStatus status) {
        return status != null ? status : BookCopyStatus.AVAILABLE;
    }

    @Mapping(target = "status", source = "status")
    void updateBookCopy(@MappingTarget BookCopy bookCopy, BookCopyUpdateRequest request);

    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    BookCopyResponse toBookCopyResponse(BookCopy bookCopy);
}