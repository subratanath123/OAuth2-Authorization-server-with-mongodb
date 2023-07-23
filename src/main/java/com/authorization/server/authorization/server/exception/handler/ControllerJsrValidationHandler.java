package com.authorization.server.authorization.server.exception.handler;

import com.authorization.server.authorization.server.exception.exceptions.CustomFieldError;
import com.authorization.server.authorization.server.exception.exceptions.FieldErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class ControllerJsrValidationHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        FieldErrorResponse fieldErrorResponse = new FieldErrorResponse();

        Set<CustomFieldError> fieldErrors = new HashSet<>();

        ex.getBindingResult()
                .getAllErrors()
                .forEach((error) -> {
                    CustomFieldError fieldError = new CustomFieldError();

                    fieldError.setField(((FieldError) error).getField());
                    fieldError.setMessage(error.getDefaultMessage());

                    fieldErrors.add(fieldError);
                });

        fieldErrorResponse.setFieldErrors(new ArrayList<>(fieldErrors));

        return new ResponseEntity<>(fieldErrorResponse, status);
    }
}