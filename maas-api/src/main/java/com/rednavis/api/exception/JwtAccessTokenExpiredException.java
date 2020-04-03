package com.rednavis.api.exception;

public class JwtAccessTokenExpiredException extends JwtException {

  public JwtAccessTokenExpiredException(String message) {
    super(message);
  }

}
