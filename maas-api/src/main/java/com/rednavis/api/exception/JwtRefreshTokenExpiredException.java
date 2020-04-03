package com.rednavis.api.exception;

public class JwtRefreshTokenExpiredException extends JwtException {

  public JwtRefreshTokenExpiredException(String message) {
    super(message);
  }

}
