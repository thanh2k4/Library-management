package com.example.librarymanagementbackend.configuration;

import com.example.librarymanagementbackend.constants.UserRole;
import com.example.librarymanagementbackend.entity.Role;
import com.example.librarymanagementbackend.entity.User;
import com.example.librarymanagementbackend.exception.AppException;
import com.example.librarymanagementbackend.exception.ErrorCode;
import com.example.librarymanagementbackend.repository.RoleRepository;
import com.example.librarymanagementbackend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    @Bean
    ApplicationRunner applicationRunner (UserRepository userRepository) {
        return args -> {
            if (roleRepository.findByName(UserRole.ADMIN).isEmpty()) {
                roleRepository.save(Role.builder()
                        .name(UserRole.ADMIN)
                        .build());
            }
            if (roleRepository.findByName(UserRole.STAFF).isEmpty()) {
                roleRepository.save(Role.builder()
                        .name(UserRole.STAFF)
                        .build());
            }
            if (roleRepository.findByName(UserRole.USER).isEmpty()) {
                roleRepository.save(Role.builder()
                        .name(UserRole.USER)
                        .build());
            }
            if (userRepository.findByName("admin").isEmpty()) {
                userRepository.save(User.builder()
                        .name("admin")
                        .userName("admin")
                        .password(passwordEncoder.encode("admin"))
                        .email("admin@gmail.com")
                        .role(roleRepository.findByName(UserRole.ADMIN).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED)))
                        .build());

                log.warn("Default admin user created with username: admin and password: admin");
            }

        };
    }
}
