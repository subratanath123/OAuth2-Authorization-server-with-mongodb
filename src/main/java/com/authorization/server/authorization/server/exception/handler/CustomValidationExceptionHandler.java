package com.authorization.server.authorization.server.exception.handler;

import com.authorization.server.authorization.server.exception.exceptions.CustomFieldError;
import com.authorization.server.authorization.server.exception.exceptions.FieldErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class CustomValidationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        FieldErrorResponse fieldErrorResponse = new FieldErrorResponse();

        Set<CustomFieldError> errors = new HashSet<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            CustomFieldError error = new CustomFieldError();

            String[] propertyPaths = violation.getPropertyPath().toString().split("\\.");

            error.setField(propertyPaths[propertyPaths.length - 1]);
            error.setMessage(violation.getMessage());

            errors.add(error);
        }

        fieldErrorResponse.setFieldErrors(new ArrayList<>(errors));

        return new ResponseEntity<>(fieldErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
