package com.authorization.server.authorization.server.exception.exceptions;

import java.io.Serializable;
import java.util.List;

public class FieldErrorResponse implements Serializable {
    private List<CustomFieldError> fieldErrors;

    public FieldErrorResponse() {
    }

    public List<CustomFieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<CustomFieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
