package com.rednavis.shared.rest.response;

import static com.rednavis.shared.util.StringUtils.BEARER;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponse {

  @Builder.Default
  private String tokenType = BEARER;
  private String accessToken;
  private int accessTokenExpiration;
  private String refreshToken;
  private int refreshTokenExpiration;
}
