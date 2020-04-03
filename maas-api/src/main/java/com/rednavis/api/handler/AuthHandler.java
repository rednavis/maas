package com.rednavis.api.handler;

import static com.rednavis.webflux.util.RouteUtils.createBadRequest;
import static com.rednavis.webflux.util.RouteUtils.createOkResponse;

import com.rednavis.api.service.AuthService;
import com.rednavis.api.service.CurrentUserService;
import com.rednavis.shared.rest.request.RefreshTokenRequest;
import com.rednavis.shared.rest.request.SignInRequest;
import com.rednavis.shared.rest.request.SignUpRequest;
import com.rednavis.shared.rest.response.SignInResponse;
import com.rednavis.shared.rest.response.SignUpResponse;
import com.rednavis.shared.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthHandler {

  private final AuthService authService;
  private final CurrentUserService currentUserService;

  /**
   * signIn.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> signIn(ServerRequest request) {
    return request.bodyToMono(SignInRequest.class)
        .flatMap(signInRequest -> createOkResponse(authService.signIn(signInRequest), SignInResponse.class))
        .switchIfEmpty(createBadRequest("SignInRequest cann't be empty"));
  }

  /**
   * signUp.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> signUp(ServerRequest request) {
    return request.bodyToMono(SignUpRequest.class)
        .flatMap(signUpRequest -> createOkResponse(authService.signUp(signUpRequest), SignUpResponse.class))
        .switchIfEmpty(createBadRequest("SignUpRequest cann't be empty"));
  }

  /**
   * refreshToken.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> refreshToken(ServerRequest request) {
    return request.bodyToMono(RefreshTokenRequest.class)
        .flatMap(refreshTokenRequest -> createOkResponse(authService.refreshToken(refreshTokenRequest), SignInResponse.class))
        .switchIfEmpty(createBadRequest("RefreshTokenRequest cann't be empty"));
  }

  /**
   * currentUser.
   *
   * @param request request
   * @return
   */
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public Mono<ServerResponse> currentUser(ServerRequest request) {
    return createOkResponse(currentUserService.getCurrentUser(), CurrentUser.class);
  }
}
