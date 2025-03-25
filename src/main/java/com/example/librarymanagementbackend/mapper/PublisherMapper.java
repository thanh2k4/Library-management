package com.example.librarymanagementbackend.mapper;

import com.example.librarymanagementbackend.dto.publisher.request.PublisherCreationRequest;
import com.example.librarymanagementbackend.dto.publisher.request.PublisherUpdateRequest;
import com.example.librarymanagementbackend.dto.publisher.response.PublisherResponse;
import com.example.librarymanagementbackend.entity.Publisher;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PublisherMapper {
    Publisher toPublisher(PublisherCreationRequest request);

    PublisherResponse toPublisherResponse(Publisher publisher);

    void updatePublisher(@MappingTarget Publisher publisher, PublisherUpdateRequest request);
}
