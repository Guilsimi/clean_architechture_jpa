package com.example.cleanarchmodel.infra.adapters.handlers;

import com.example.cleanarchmodel.core.exceptions.UserAlreadyExistsException;
import com.example.cleanarchmodel.core.exceptions.UserNotFoundException;
import com.example.cleanarchmodel.core.exceptions.UserOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    private ResponseEntity<UserExceptionResponse> userAlreadyExistsHandler(UserAlreadyExistsException exception) {
        return ResponseEntity.status(409).body(
                new UserExceptionResponse(HttpStatus.CONFLICT.toString(), exception.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<UserExceptionResponse> userNotFoundHandler(UserNotFoundException exception) {
        return ResponseEntity.status(404).body(
                new UserExceptionResponse(HttpStatus.NOT_FOUND.toString(), exception.getMessage())
        );
    }

    @ExceptionHandler(UserOperationException.class)
    private ResponseEntity<UserExceptionResponse> userOperationErrorHandler(UserOperationException exception) {
        return ResponseEntity.status(400).body(
                new UserExceptionResponse(HttpStatus.BAD_REQUEST.toString(), exception.getMessage())
        );
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<UserExceptionResponse> genericExceptionHandler(Exception exception) {
        return ResponseEntity.status(500).body(
                new UserExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "An unexpected error occurred. Please try again later.")
        );
    }
}
