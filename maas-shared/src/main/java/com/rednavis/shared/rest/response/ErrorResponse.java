package com.rednavis.shared.rest.response;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class ErrorResponse {

  private Instant timestamp;
  private String path;
  private String exceptionId;
  private String error;
  private String message;
  private Integer status; // We'd need it as integer in JSON serialization
}
