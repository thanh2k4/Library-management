package com.example.librarymanagementbackend.service;

import com.example.librarymanagementbackend.dto.author.request.AuthorCreationRequest;
import com.example.librarymanagementbackend.dto.author.request.AuthorGetAllRequest;
import com.example.librarymanagementbackend.dto.author.request.AuthorUpdateRequest;
import com.example.librarymanagementbackend.dto.author.response.AuthorResponse;
import com.example.librarymanagementbackend.dto.base.response.BaseGetAllResponse;
import com.example.librarymanagementbackend.entity.Author;
import com.example.librarymanagementbackend.exception.AppException;
import com.example.librarymanagementbackend.exception.ErrorCode;
import com.example.librarymanagementbackend.mapper.AuthorMapper;
import com.example.librarymanagementbackend.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@Slf4j
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorResponse createAuthor(AuthorCreationRequest request) {
        Author author = authorMapper.toAuthor(request);
        try {
            author = authorRepository.save(author);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.AUTHOR_EXISTED);
        }
        return authorMapper.toAuthorResponse(author);
    }

    public BaseGetAllResponse<AuthorResponse> getAllAuthors(AuthorGetAllRequest request) {
        log.info("in getAllAuthors function");

        Long skipCount = request.getSkipCount() != null ? request.getSkipCount() : 0;
        Long maxResultCount = request.getMaxResultCount() != null ? request.getMaxResultCount() : 10;
        String name = (request.getName() == null || request.getName().isEmpty()) ? null : request.getName();

        List<AuthorResponse> authorResponseList = authorRepository.findAllByFilters(name)
                .stream()
                .skip(skipCount)
                .limit(maxResultCount)
                .map(authorMapper::toAuthorResponse)
                .collect(Collectors.toList());

        return BaseGetAllResponse.<AuthorResponse>builder()
                .data(authorResponseList)
                .totalRecords(authorRepository.countByFilters(name))
                .build();
    }

    public AuthorResponse updateAuthor(AuthorUpdateRequest request) {
        Author author = authorRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_EXISTED));
        authorMapper.updateAuthor(author, request);
        author = authorRepository.save(author);

        return authorMapper.toAuthorResponse(author);
    }

    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new AppException(ErrorCode.AUTHOR_NOT_EXISTED);
        }
        authorRepository.deleteById(id);
    }

    public AuthorResponse getAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_EXISTED));
        return authorMapper.toAuthorResponse(author);
    }
}