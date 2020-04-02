package com.rednavis.auth.route;

import static com.rednavis.shared.util.RestUrlUtils.GENERATE_PASSWORD_HASH_URL;
import static com.rednavis.shared.util.RestUrlUtils.PASSWORD_URL;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import com.rednavis.auth.handler.PasswordHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Configuration
@RequiredArgsConstructor
public class PasswordRoute {

  private final PasswordHandler passwordHandler;

  /**
   * userRouteFunction.
   *
   * @return
   */
  @Bean
  public RouterFunction userRouteFunction() {
    return RouterFunctions.route()
        .path(PASSWORD_URL, builder -> builder
            .GET(GENERATE_PASSWORD_HASH_URL, accept(MediaType.APPLICATION_JSON), passwordHandler::generatePasswordHash)
            .build())
        .build();
  }
}
