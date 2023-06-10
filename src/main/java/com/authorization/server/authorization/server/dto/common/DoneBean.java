package com.authorization.server.authorization.server.dto.common;

import java.io.Serializable;

public class DoneBean implements Serializable {

    public DoneBean(String message, Type type) {
        this.message = message;
        this.type = type;
    }

    private String  message;
    private Type  type;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
