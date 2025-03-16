package com.example.librarymanagementbackend.exception;

import com.example.librarymanagementbackend.dto.base.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(Exception exception) {
        log.error("Unhandled Exception: ", exception);

        ApiResponse<Object> apiResponse = ApiResponse.<Object>builder()
                .success(false)
                .code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
                .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
                .error(exception.getMessage())
                .build();

        return ResponseEntity.badRequest().body(apiResponse);
    }

    // Xử lý các Exception tùy chỉnh (AppException)
    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse<Object>> handleAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        ApiResponse<Object> apiResponse = ApiResponse.<Object>builder()
                .success(false)
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .error(exception.getMessage())
                .build();

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }


    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));

        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }
}
