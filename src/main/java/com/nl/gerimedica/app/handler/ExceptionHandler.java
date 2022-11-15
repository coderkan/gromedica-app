package com.nl.gerimedica.app.handler;

import com.nl.gerimedica.app.exception.NotFoundEntityException;
import com.nl.gerimedica.app.exception.UnsupportedFileFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<GenericErrorMessage> handleException(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        GenericErrorMessage responseBody = new GenericErrorMessage(status.value(), "Internal system error");
        return ResponseEntity.status(status).body(responseBody);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UnsupportedFileFormatException.class)
    public ResponseEntity<GenericErrorMessage> handleUnsupportedFormatException(UnsupportedFileFormatException ex) {
        GenericErrorMessage responseBody = new GenericErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<GenericErrorMessage> handleNotFoundException(NotFoundEntityException ex) {
        GenericErrorMessage responseBody = new GenericErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
