package com.lisaanna.ws_todo.exception;

import com.lisaanna.ws_todo.controller.ApiError;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ApiError> buildResponse(
            HttpStatus status,
            String error,
            String message,
            HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
                status.value(),
                error,
                message,
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(apiError);
    }

    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<ApiError> handleRequestNotPermitted(RequestNotPermitted e, HttpServletRequest request) {
        return buildResponse(
                HttpStatus.TOO_MANY_REQUESTS,
                "Too Many Requests",
                "Rate limit reached, try again later",
                request
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException e, HttpServletRequest request) {

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Runtime Exception",
                e.getMessage(),
                request
        );
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiError> handleNullPointerException(NullPointerException e, HttpServletRequest request) {
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Null Pointer Exception",
                e.getMessage(),
                request
        );
    }
}
