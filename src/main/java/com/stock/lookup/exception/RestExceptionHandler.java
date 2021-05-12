package com.stock.lookup.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
          HttpMessageNotReadableException ex,
          HttpHeaders headers,
          HttpStatus status,
          WebRequest request) {
    List<String> error = Arrays.asList("Malformed JSON request");
    return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
  }

  // Custom Exceptions:

  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<Object> handleEntityNotFound(
          EntityNotFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    List<String> error = Arrays.asList("resource not found");
    ApiError apiError = new ApiError(NOT_FOUND, error, ex);
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(DataAccessException.class)
  protected ResponseEntity<Object> handleDataAccessException(
          DataAccessException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    List<String> error = Arrays.asList("sql exception");
    ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR, error, ex);
    return buildResponseEntity(apiError);
  }

  private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
          MethodArgumentNotValidException ex,
          HttpHeaders headers,
          HttpStatus status,
          WebRequest request) {
    List<String> validationErrors =
            ex.getBindingResult().getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
    ApiError apiError = new ApiError(BAD_REQUEST, validationErrors, ex);
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Object> handleConstraintViolation(
          ConstraintViolationException exception, WebRequest request) {
    List<String> validationErrors =
            exception.getConstraintViolations().stream()
                    .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                    .collect(Collectors.toList());
    ApiError apiError = new ApiError(BAD_REQUEST, validationErrors, exception);
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(PathVariableValidationException.class)
  public ResponseEntity<Object> handleAllPathValidations(
          PathVariableValidationException exception) {
    List<String> validationErrors = Arrays.asList("path variable validation failed");
    ApiError apiError = new ApiError(BAD_REQUEST, validationErrors, exception);
    return buildResponseEntity(apiError);
  }
}
