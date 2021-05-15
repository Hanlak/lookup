package com.stock.lookup.exception;

public class PathVariableValidationException extends RuntimeException {

  protected PathVariableValidationException() {}

  public PathVariableValidationException(String message) {
    super(message);
  }
}
