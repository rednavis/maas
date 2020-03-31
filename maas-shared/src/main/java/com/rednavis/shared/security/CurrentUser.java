package com.rednavis.shared.security;

import com.rednavis.shared.dto.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class CurrentUser {

  private String id;
  private String firstName;
  private String lastName;
  private String userName;
  private String email;
  private UserRole roles;
}