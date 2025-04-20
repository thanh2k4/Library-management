package com.example.librarymanagementbackend.controller;

import com.example.librarymanagementbackend.dto.base.response.ApiResponse;
import com.example.librarymanagementbackend.dto.base.response.BaseGetAllResponse;
import com.example.librarymanagementbackend.dto.publisher.request.PublisherCreationRequest;
import com.example.librarymanagementbackend.dto.publisher.request.PublisherGetAllRequest;
import com.example.librarymanagementbackend.dto.publisher.request.PublisherUpdateRequest;
import com.example.librarymanagementbackend.dto.publisher.response.PublisherResponse;
import com.example.librarymanagementbackend.service.PublisherService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publishers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PublisherController {
    PublisherService publisherService;

    @PostMapping("/Create")
    ApiResponse<PublisherResponse> createPublisher(@RequestBody PublisherCreationRequest request) {
        return ApiResponse.<PublisherResponse>builder().result(publisherService.createPublisher(request)).build();
    }

    @GetMapping("/GetAll")
    ApiResponse<BaseGetAllResponse<PublisherResponse>> getAllPublishers(
            @RequestParam(value = "skipCount", defaultValue = "0") Long skipCount,
            @RequestParam(value = "maxResultCount", defaultValue = "10") Long maxResultCount,
            @RequestParam(value = "name", required = false) String name) {

        PublisherGetAllRequest request = new PublisherGetAllRequest();
        request.setSkipCount(skipCount);
        request.setMaxResultCount(maxResultCount);
        request.setName(name);

        return ApiResponse.<BaseGetAllResponse<PublisherResponse>>builder()
                .result(publisherService.getAllPublishers(request))
                .build();
    }

    @GetMapping("/GetById")
    ApiResponse<PublisherResponse> getPublisherById(@RequestParam Long id) {
        return ApiResponse.<PublisherResponse>builder().result(publisherService.getPublisher(id)).build();
    }

    @DeleteMapping("/Delete")
    ApiResponse<String> deletePublisher(@RequestParam Long id) {
        publisherService.deletePublisher(id);
        return ApiResponse.<String>builder().result("Publisher deleted successfully").build();
    }

    @PutMapping("/Update")
    ApiResponse<PublisherResponse> updatePublisher(@RequestBody PublisherUpdateRequest request) {
        return ApiResponse.<PublisherResponse>builder()
                .result(publisherService.updatePublisher(request))
                .build();
    }
}