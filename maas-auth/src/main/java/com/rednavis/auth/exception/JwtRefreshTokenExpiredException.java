package com.rednavis.auth.exception;

public class JwtRefreshTokenExpiredException extends JwtException {

  public JwtRefreshTokenExpiredException(String message) {
    super(message);
  }

}
