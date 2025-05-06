package com.example.librarymanagementbackend.service;

import com.example.librarymanagementbackend.dto.authen.request.AuthenticationRequest;
import com.example.librarymanagementbackend.dto.authen.request.IntrospectRequest;
import com.example.librarymanagementbackend.dto.authen.request.LogoutRequest;
import com.example.librarymanagementbackend.dto.authen.request.RefreshRequest;
import com.example.librarymanagementbackend.dto.authen.response.AuthenticationResponse;
import com.example.librarymanagementbackend.dto.authen.response.IntrospectResponse;
import com.example.librarymanagementbackend.entity.Permission;
import com.example.librarymanagementbackend.entity.RefreshToken;
import com.example.librarymanagementbackend.entity.User;
import com.example.librarymanagementbackend.exception.AppException;
import com.example.librarymanagementbackend.exception.ErrorCode;
import com.example.librarymanagementbackend.repository.RefreshTokenRepository;
import com.example.librarymanagementbackend.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    RefreshTokenRepository refreshTokenRepository;
    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${jwt.refresh-token.secret}")
    protected String refreshTokenSecret;

    @NonFinal
    @Value("${jwt.refresh-token.duration}")
    protected long refreshTokenDuration;

    @NonFinal
    @Value("${jwt.access-token.secret}")
    protected String accessTokenSecret;

    @NonFinal
    @Value("${jwt.access-token.duration}")
    protected long accessTokenDuration;

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var accessToken = request.getAccessToken();
        boolean isValid = true;
        try {
            verifyAccessToken(accessToken);
        } catch (AppException e) {
            isValid = false;
        }
        return IntrospectResponse.builder().valid(isValid).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository
                .findByName(request.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        var refreshToken = generateRefreshToken(user);
        var accessToken = generateAccessToken(user);

        List<String> permissions = user.getRole().getPermissions().stream()
                .map(Permission::getName)
                .toList();

        return AuthenticationResponse.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .permissions(permissions)
                .authenticated(true)
                .build();
    }

    public AuthenticationResponse refresh(RefreshRequest request) throws ParseException, JOSEException {
        var signedJWT = verifyRefreshToken(request.getRefreshToken());

        var username = signedJWT.getJWTClaimsSet().getSubject();
        var user = userRepository.findByName(username)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
        refreshTokenRepository.deleteById(username);
        var refreshToken = generateRefreshToken(user);
        var accessToken = generateAccessToken(user);

        return AuthenticationResponse.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .authenticated(true)
                .build();
    }

    private String generateRefreshToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getName())
                .issuer("Nhom34")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(refreshTokenDuration, ChronoUnit.SECONDS)))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(refreshTokenSecret.getBytes()));
            refreshTokenRepository.save(RefreshToken.builder()
                    .username(user.getName())
                    .refreshToken(jwsObject.serialize())
                    .build());
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create refresh-token", e);
            throw new RuntimeException(e);
        }
    }

    private String generateAccessToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getName())
                .issuer("Nhom34")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(refreshTokenDuration, ChronoUnit.SECONDS)))
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(accessTokenSecret.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create access-token", e);
            throw new RuntimeException(e);
        }
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try {
            var signToken = verifyRefreshToken(request.getRefreshToken());
            String username = signToken.getJWTClaimsSet().getSubject();
            refreshTokenRepository.deleteById(username);
        } catch (AppException exception) {
            log.info("Token already expired");
        }
    }

    private SignedJWT verifyRefreshToken(String token) throws ParseException, JOSEException {
        JWSVerifier verifier = new MACVerifier(refreshTokenSecret.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var refreshTokenOpt = refreshTokenRepository.findById(signedJWT.getJWTClaimsSet().getSubject());
        if (refreshTokenOpt.isEmpty() || !refreshTokenOpt.get().getRefreshToken().equals(token)) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;
    }

    private SignedJWT verifyAccessToken(String token) throws ParseException, JOSEException {
        JWSVerifier verifier = new MACVerifier(accessTokenSecret.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (user.getRole() != null) {
            stringJoiner.add("ROLE_" + user.getRole().getName().toString());
            if (!CollectionUtils.isEmpty(user.getRole().getPermissions())) {
                user.getRole().getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
            }
        }
        return stringJoiner.toString();

    }
}
