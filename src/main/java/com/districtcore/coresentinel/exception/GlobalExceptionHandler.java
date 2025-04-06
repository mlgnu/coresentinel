package com.districtcore.coresentinel.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
        List<String> errors = Arrays.asList(Objects.requireNonNull(ex.getReason()).split("; "));
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return new ResponseEntity<Object>(errorResponse, ex.getStatusCode());
    }
}
