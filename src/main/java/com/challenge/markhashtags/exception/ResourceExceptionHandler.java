package com.challenge.markhashtags.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<Error> handleUnauthorizedException(UnauthorizedException ex) {
    Error error = new Error(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), new Date(), null);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Error> handleNotFoundException(NotFoundException ex) {
    Error error = new Error(HttpStatus.NOT_FOUND.value(), ex.getMessage(), new Date(), null);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(InternalServerErrorException.class)
  public ResponseEntity<Error> handleInternalServerErrorException(InternalServerErrorException ex) {
    Error error = new Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), new Date(), null);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}
