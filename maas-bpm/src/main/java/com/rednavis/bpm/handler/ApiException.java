package com.rednavis.bpm.handler;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException {

  LocalDateTime timestamp;
  int status;
  String error;
  List<String> messages;

  /**
   * ApiException.
   *
   * @param status   status
   * @param messages messages
   */
  public ApiException(HttpStatus status, String... messages) {
    this.timestamp = LocalDateTime.now();
    this.status = status.value();
    this.error = status.getReasonPhrase();
    this.messages = Arrays.asList(messages);
  }
}
