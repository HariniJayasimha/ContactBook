package com.plivo.contactbook.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private ObjectNode response = null;
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_CAUSE = "errorCause";
    private static final String TEMP_SIGNUP_TOKEN = "tempSignupToken";
    private static final String ALLOWED_TO_SIGNUP = "allowedToSignUp";

    // 401
    @ExceptionHandler({ UnAuthorizedException.class})
    public ResponseEntity<Object> handleAuthorizationException(final Exception ex, final WebRequest request) {
        response = JsonNodeFactory.instance.objectNode();

        response.put(ERROR_MESSAGE, ex.getLocalizedMessage());
        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    // 400
    @ExceptionHandler({ BadRequestException.class, InvalidFormatException.class })
    public ResponseEntity<Object> handleBadRequestException(final BadRequestException ex, final WebRequest request) {
        response = JsonNodeFactory.instance.objectNode();

        response.put(ERROR_MESSAGE, ex.getLocalizedMessage());
        response.put(ERROR_CAUSE, ex.getErrorCause());
        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(final AccessDeniedException ex,  final WebRequest request){
        response = JsonNodeFactory.instance.objectNode();

        response.put(ERROR_MESSAGE, ex.getLocalizedMessage());
        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    // 404
    @ExceptionHandler({ NotFoundException.class, RequestRejectedException.class })
    public ResponseEntity<Object> handleNotFound(final NotFoundException ex, final WebRequest request) {
        response = JsonNodeFactory.instance.objectNode();

        response.put(ERROR_MESSAGE, ex.getLocalizedMessage());
        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    // 500 Generic exception
    @ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class,
            JsonParseException.class, RuntimeException.class})
    public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
        response = JsonNodeFactory.instance.objectNode();

        response.put(ERROR_MESSAGE, "OOPS!! something went wrong");
        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
