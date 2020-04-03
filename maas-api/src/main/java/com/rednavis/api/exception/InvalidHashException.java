package com.rednavis.api.exception;

public class InvalidHashException extends Exception {

  public InvalidHashException(String message) {
    super(message);
  }

  public InvalidHashException(String message, Throwable source) {
    super(message, source);
  }
}
