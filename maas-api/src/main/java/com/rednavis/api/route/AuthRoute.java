package com.rednavis.api.route;

import static com.rednavis.shared.util.RestUrlUtils.AUTH_URL;
import static com.rednavis.shared.util.RestUrlUtils.AUTH_URL_CURRENTUSER;
import static com.rednavis.shared.util.RestUrlUtils.AUTH_URL_REFRESH_TOKEN;
import static com.rednavis.shared.util.RestUrlUtils.AUTH_URL_SIGNIN;
import static com.rednavis.shared.util.RestUrlUtils.AUTH_URL_SIGNUP;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import com.rednavis.api.handler.AuthHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Configuration
@RequiredArgsConstructor
public class AuthRoute {

  private final AuthHandler authHandler;

  /**
   * userRouteFunction.
   *
   * @return
   */
  @Bean
  public RouterFunction userRouteFunction() {
    return RouterFunctions.route()
        .path(AUTH_URL, builder -> builder
            .POST(AUTH_URL_SIGNIN, accept(MediaType.APPLICATION_JSON), authHandler::signIn)
            .POST(AUTH_URL_SIGNUP, accept(MediaType.APPLICATION_JSON), authHandler::signUp)
            .POST(AUTH_URL_REFRESH_TOKEN, accept(MediaType.APPLICATION_JSON), authHandler::refreshToken)
            .GET(AUTH_URL_CURRENTUSER, accept(MediaType.APPLICATION_JSON), authHandler::currentUser)
            .build())
        .build();
  }
}