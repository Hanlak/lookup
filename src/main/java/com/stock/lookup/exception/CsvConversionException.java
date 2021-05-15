package com.stock.lookup.exception;

public class CsvConversionException extends RuntimeException {

  protected CsvConversionException() {}

  public CsvConversionException(String message) {
    super(message);
  }
}
