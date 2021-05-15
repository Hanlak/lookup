package com.stock.lookup.util;

import com.stock.lookup.exception.PathVariableValidationException;

public class RequestValidator {
  public static void validatePathVariables(String fieldName, String fieldKeyName) {
    if (fieldName == null || fieldName.trim().isEmpty()) {
      throw new PathVariableValidationException(
          fieldKeyName + " validation failed and the field should not be empty or null");
    }
  }

  public static void validatePathVariables(
      String fieldName1, String fieldKeyName1, String fieldName2, String fieldKeyName2) {
    if ((fieldName1 == null || fieldName1.trim().isEmpty())
        && (fieldName2 == null || fieldName2.trim().isEmpty())) {
      String message =
          fieldKeyName1
              + " and "
              + fieldKeyName2
              + " validations failed and the fields should not be empty or null";
      throw new PathVariableValidationException(message);
    } else if (fieldName1 == null || fieldName1.trim().isEmpty()) {
      String message =
          fieldKeyName1 + " vaidation failed and the field should not be empty or null";
      throw new PathVariableValidationException(message);
    } else if (fieldName2 == null || fieldName2.trim().isEmpty()) {
      String message =
          fieldKeyName2 + " vaidation failed and the field should not be empty or null";
      throw new PathVariableValidationException(message);
    }
  }
}
