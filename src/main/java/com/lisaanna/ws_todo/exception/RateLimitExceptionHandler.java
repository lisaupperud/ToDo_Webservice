package com.lisaanna.ws_todo.exception;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

// To handle exception thrown when limit for requests are reached

public class RateLimitExceptionHandler extends RuntimeException {

    /*@ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<Map<String, Object>> handleRequestNotPermitted(RequestNotPermitted e, HttpServletRequest request) {

        // Using a HashMap for key-values pairs
        // Output is now in JSON format for clearer output message when exception is thrown
        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("timestamp", System.currentTimeMillis());
        jsonBody.put("status", HttpStatus.TOO_MANY_REQUESTS.value());
        jsonBody.put("error", "Too Many Requests Attempted");
        jsonBody.put("message", "Rate limit reached, try again later");
        jsonBody.put("path", request.getRequestURI()); // so we can get the URI from the request

        return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .body(jsonBody);
    }*/
}
