package com.rednavis.auth.exception;

public class JwtAccessTokenExpiredException extends JwtException {

  public JwtAccessTokenExpiredException(String message) {
    super(message);
  }

}
