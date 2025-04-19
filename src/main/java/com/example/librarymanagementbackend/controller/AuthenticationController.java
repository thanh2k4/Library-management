package com.example.librarymanagementbackend.controller;

import com.example.librarymanagementbackend.dto.base.response.ApiResponse;
import com.example.librarymanagementbackend.dto.authen.request.AuthenticationRequest;
import com.example.librarymanagementbackend.dto.authen.request.IntrospectRequest;
import com.example.librarymanagementbackend.dto.authen.request.LogoutRequest;
import com.example.librarymanagementbackend.dto.authen.request.RefreshRequest;
import com.example.librarymanagementbackend.dto.authen.response.AuthenticationResponse;
import com.example.librarymanagementbackend.dto.authen.response.IntrospectResponse;
import com.example.librarymanagementbackend.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
        AuthenticationService authenticationService;

        @PostMapping("/Login")
        ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
                var result = authenticationService.authenticate(request);
                return ApiResponse.<AuthenticationResponse>builder()
                                .result(result)
                                .build();
        }

        @PostMapping("/Introspect")
        ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
                        throws JOSEException, ParseException {
                var result = authenticationService.introspect(request);
                return ApiResponse.<IntrospectResponse>builder()
                                .result(result)
                                .build();
        }

        @PostMapping("/Logout")
        ApiResponse<Void> logout(@RequestBody LogoutRequest request)
                        throws JOSEException, ParseException {
                authenticationService.logout(request);
                return ApiResponse.<Void>builder()
                                .build();
        }

        @PostMapping("/Refresh")
        ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshRequest request)
                        throws JOSEException, ParseException {
                var result = authenticationService.refreshToken(request);
                return ApiResponse.<AuthenticationResponse>builder()
                                .result(result)
                                .build();
        }

}
