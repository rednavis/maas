package com.rednavis.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  //private final PasswordService passwordService;
  //private final JwtTokenService jwtTokenService;
  //private final MaasDataUserRestService maasDataUserRestService;
  //private final MaasDataRefreshTokenRestService maasDataRefreshTokenRestService;

  //public Mono<SignInResponse> signIn(SignInRequest signInRequest) {
  //  return Mono.just(StringUtils.isEmailValid(signInRequest.getUserName()))
  //      .flatMap(
  //          isValid -> (isValid.booleanValue()) ? findByEmail(signInRequest.getUserName()) : findByUserName(signInRequest.getUserName()))
  //      .filter(user -> passwordService.validatePassword(user.getPassword(), signInRequest.getPassword()))
  //      .switchIfEmpty(Mono.error(new BadCredentialsException("Wrong email or password")))
  //      .flatMap(this::generateTokens);
  //}

  //private Mono<User> findByEmail(String email) {
  //  return maasDataUserRestService.findByEmail(email)
  //      .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found [email: " + email + "]")));
  //}

  //private Mono<User> findByUserName(String userName) {
  //  return maasDataUserRestService.findByUserName(userName)
  //      .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found [userName: " + userName + "]")));
  //}

  //public Mono<SignUpResponse> signUp(SignUpRequest signUpRequest) {
  //  return userService.findByEmail(signUpRequest.getEmail())
  //      .switchIfEmpty(Mono.error(new ConflictException("Wrong email [email: " + signUpRequest.getEmail() + "] is already taken")))
  //      .then(userService.save(createNewUser(signUpRequest)))
  //      .map(user -> SignUpResponse.builder()
  //          .id(user.getId())
  //          .build());
  //}

  //public Mono<SignInResponse> refreshToken(RefreshTokenRequest refreshTokenRequest) {
  //  return Mono.fromSupplier(refreshTokenRequest::getRefreshToken)
  //      .map(token -> jwtTokenService.checkToken(JwtTokenEnum.JWT_REFRESH_TOKEN, token))
  //      .filter(Objects::nonNull)
  //      .flatMap(signedJwt -> maasDataRefreshTokenRestService.findByRefreshToken(signedJwt.serialize()))
  //      .filter(Objects::nonNull)
  //      .flatMap(refreshToken -> maasDataRefreshTokenRestService.deleteById(refreshToken.getId())
  //          .then(maasDataUserRestService.findById(refreshToken.getUserId())))
  //      .flatMap(this::generateTokens);
  //}

  //private Mono<SignInResponse> generateTokens(User user) {
  //  CurrentUser currentUser = CURRENT_USER_MAPPER.userToCurrentUser(user);
  //
  //  long currentTime = Instant.now().toEpochMilli();
  //  JwtTokenInfo accessToken = jwtTokenService.generateToken(JwtTokenEnum.JWT_ACCESS_TOKEN, currentUser, currentTime);
  //  JwtTokenInfo refreshToken = jwtTokenService.generateToken(JwtTokenEnum.JWT_REFRESH_TOKEN, currentUser, currentTime);
  //
  //  return saveRefreshToken(user, refreshToken)
  //      .thenReturn(SignInResponse.builder()
  //          .accessToken(accessToken.getToken())
  //          .accessTokenExpiration(accessToken.getTokenExpirationInSec())
  //          .refreshToken(refreshToken.getToken())
  //          .refreshTokenExpiration(refreshToken.getTokenExpirationInSec())
  //          .build());
  //}

  //private Mono<RefreshToken> saveRefreshToken(User user, JwtTokenInfo jwtTokenInfo) {
  //  RefreshToken refreshToken = RefreshToken.builder()
  //      .refreshToken(jwtTokenInfo.getToken())
  //      .expiration(Instant.ofEpochMilli(jwtTokenInfo.getExpirationTime()))
  //      .userId(user.getId())
  //      .build();
  //  return maasDataRefreshTokenRestService.insert(refreshToken);
  //}

  //private User createNewUser(SignUpRequest signUpRequest) {
  //  return User.builder()
  //      .firstName(signUpRequest.getFirstName())
  //      .lastName(signUpRequest.getLastName())
  //      .email(signUpRequest.getEmail())
  //      .password(passwordService.generatePassword(signUpRequest.getPassword()))
  //      .roles(Set.of(RoleEnum.ROLE_USER))
  //      .build();
  //}
}
