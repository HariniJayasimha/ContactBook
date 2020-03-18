package com.plivo.contactbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String errorCause;

    public BadRequestException() {
        super();
        this.errorCause = "";
    }

    public BadRequestException(String message) {
        super(message);
        this.errorCause = "";
    }

    public BadRequestException(String message, String cause) {
        super(message);
        this.errorCause = cause;
    }

    public String getErrorCause() {
        return errorCause;
    }
}
