package com.rednavis.api.service;

import static com.rednavis.api.mapper.MapperProvider.CURRENT_USER_MAPPER;

import java.time.Instant;
import java.util.Objects;
import com.rednavis.api.exception.ConflictException;
import com.rednavis.api.jwt.JwtTokenEnum;
import com.rednavis.api.jwt.JwtTokenInfo;
import com.rednavis.api.jwt.JwtTokenService;
import com.rednavis.shared.dto.RefreshToken;
import com.rednavis.shared.dto.User;
import com.rednavis.shared.dto.UserRole;
import com.rednavis.shared.rest.request.RefreshTokenRequest;
import com.rednavis.shared.rest.request.SignInRequest;
import com.rednavis.shared.rest.request.SignUpRequest;
import com.rednavis.shared.rest.response.SignInResponse;
import com.rednavis.shared.rest.response.SignUpResponse;
import com.rednavis.shared.security.CurrentUser;
import com.rednavis.shared.util.StringUtils;
import com.rednavis.webflux.service.MaasAuthPasswordRestService;
import com.rednavis.webflux.service.MaasDataRefreshTokenRestService;
import com.rednavis.webflux.service.MaasDataUserRestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final JwtTokenService jwtTokenService;
  private final MaasAuthPasswordRestService maasAuthPasswordRestService;
  private final MaasDataUserRestService maasDataUserRestService;
  private final MaasDataRefreshTokenRestService maasDataRefreshTokenRestService;

  /**
   * signIn.
   *
   * @param signInRequest signInRequest
   * @return
   */
  public Mono<SignInResponse> signIn(SignInRequest signInRequest) {
    //TODO Remove block
    return Mono.just(StringUtils.isEmailValid(signInRequest.getUserName()))
        .flatMap(
            isValid -> (isValid.booleanValue()) ? findByEmail(signInRequest.getUserName()) : findByUserName(signInRequest.getUserName()))
        .flatMap(user -> maasAuthPasswordRestService.validatePassword(user.getPassword(), signInRequest.getPassword())
            .map(valid -> {
              if (valid) {
                return user;
              } else {
                return null;
              }
            }))
        .switchIfEmpty(Mono.error(new BadCredentialsException("Wrong email or password")))
        .flatMap(this::generateTokens);
  }

  private Mono<User> findByEmail(String email) {
    return maasDataUserRestService.findByEmail(email)
        .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found [email: " + email + "]")));
  }

  private Mono<User> findByUserName(String userName) {
    return maasDataUserRestService.findByUserName(userName)
        .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found [userName: " + userName + "]")));
  }

  /**
   * signUp.
   *
   * @param signUpRequest signUpRequest
   * @return
   */
  public Mono<SignUpResponse> signUp(SignUpRequest signUpRequest) {
    return maasDataUserRestService.findByEmail(signUpRequest.getEmail())
        .switchIfEmpty(Mono.error(new ConflictException("Wrong email [email: " + signUpRequest.getEmail() + "] is already taken")))
        .then(maasDataUserRestService.save(createNewUser(signUpRequest)))
        .map(user -> SignUpResponse.builder()
            .id(user.getId())
            .build());
  }

  /**
   * refreshToken.
   *
   * @param refreshTokenRequest refreshTokenRequest
   * @return
   */
  public Mono<SignInResponse> refreshToken(RefreshTokenRequest refreshTokenRequest) {
    return Mono.fromSupplier(refreshTokenRequest::getRefreshToken)
        .map(token -> jwtTokenService.checkToken(JwtTokenEnum.JWT_REFRESH_TOKEN, token))
        .filter(Objects::nonNull)
        .flatMap(signedJwt -> maasDataRefreshTokenRestService.findByRefreshToken(signedJwt.serialize()))
        .filter(Objects::nonNull)
        .flatMap(refreshToken -> maasDataRefreshTokenRestService.deleteById(refreshToken.getId())
            .then(maasDataUserRestService.findById(refreshToken.getUserId())))
        .flatMap(this::generateTokens);
  }

  private Mono<SignInResponse> generateTokens(User user) {
    CurrentUser currentUser = CURRENT_USER_MAPPER.userToCurrentUser(user);

    long currentTime = Instant.now().toEpochMilli();
    JwtTokenInfo accessToken = jwtTokenService.generateToken(JwtTokenEnum.JWT_ACCESS_TOKEN, currentUser, currentTime);
    JwtTokenInfo refreshToken = jwtTokenService.generateToken(JwtTokenEnum.JWT_REFRESH_TOKEN, currentUser, currentTime);

    return saveRefreshToken(user, refreshToken)
        .thenReturn(SignInResponse.builder()
            .accessToken(accessToken.getToken())
            .accessTokenExpiration(accessToken.getTokenExpirationInSec())
            .refreshToken(refreshToken.getToken())
            .refreshTokenExpiration(refreshToken.getTokenExpirationInSec())
            .build());
  }

  private Mono<RefreshToken> saveRefreshToken(User user, JwtTokenInfo jwtTokenInfo) {
    RefreshToken refreshToken = RefreshToken.builder()
        .refreshToken(jwtTokenInfo.getToken())
        .expiration(Instant.ofEpochMilli(jwtTokenInfo.getExpirationTime()))
        .userId(user.getId())
        .build();
    return maasDataRefreshTokenRestService.insert(refreshToken);
  }

  private Mono<User> createNewUser(SignUpRequest signUpRequest) {
    return maasAuthPasswordRestService.generatePasswordHash(signUpRequest.getPassword())
        .map(passwordHash -> User.builder()
            .firstName(signUpRequest.getFirstName())
            .lastName(signUpRequest.getLastName())
            .email(signUpRequest.getEmail())
            .password(passwordHash)
            .role(UserRole.ROLE_USER)
            .build());
  }
}
