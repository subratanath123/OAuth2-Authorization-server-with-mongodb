package com.authorization.server.authorization.server.exception.exceptions;

import java.io.Serializable;

public class CustomFieldError implements Serializable {

    private String field;
    private String message;

    public CustomFieldError() {
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}