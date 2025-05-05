package com.example.librarymanagementbackend.dto.authen.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    String refreshToken;
    String accessToken;
    boolean authenticated;
    private List<String> permissions;
}
