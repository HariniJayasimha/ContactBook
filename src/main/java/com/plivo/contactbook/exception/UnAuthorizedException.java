package com.plivo.contactbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnAuthorizedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnAuthorizedException() {
        super();
    }

    public UnAuthorizedException(String message) {
        super(message);
    }

}
