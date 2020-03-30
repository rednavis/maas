package com.rednavis.shared.dto;

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
public class User {

  private String id;
  private String firstName;
  private String lastName;
  private String email;
  private String userName;
  private String password;
  private UserRole role;
}
