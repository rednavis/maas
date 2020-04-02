package com.rednavis.data.route;

import static com.rednavis.shared.util.RestUrlUtils.COUNT_URL;
import static com.rednavis.shared.util.RestUrlUtils.DELETEALL_URL;
import static com.rednavis.shared.util.RestUrlUtils.DELETEBYID_URL;
import static com.rednavis.shared.util.RestUrlUtils.FINDALL_URL;
import static com.rednavis.shared.util.RestUrlUtils.FINDBYID_URL;
import static com.rednavis.shared.util.RestUrlUtils.FINDBYREFRESHTOKEN_URL;
import static com.rednavis.shared.util.RestUrlUtils.INSERT_URL;
import static com.rednavis.shared.util.RestUrlUtils.REFRESH_TOKEN_URL;
import static com.rednavis.shared.util.RestUrlUtils.SAVE_URL;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import com.rednavis.data.handler.RefreshTokenHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Configuration
@RequiredArgsConstructor
public class RefreshTokenRoute {

  private final RefreshTokenHandler refreshTokenHandler;

  /**
   * bookRouteFunction.
   *
   * @return
   */
  @Bean
  public RouterFunction refreshTokenRouteFunction() {
    return RouterFunctions.route()
        .path(REFRESH_TOKEN_URL, builder -> builder
            .POST(INSERT_URL, accept(MediaType.APPLICATION_JSON), refreshTokenHandler::insert)
            .PUT(SAVE_URL, accept(MediaType.APPLICATION_JSON), refreshTokenHandler::save)
            .GET(FINDALL_URL, accept(MediaType.APPLICATION_JSON), refreshTokenHandler::findAll)
            .GET(FINDBYID_URL, accept(MediaType.APPLICATION_JSON), refreshTokenHandler::findById)
            .GET(FINDBYREFRESHTOKEN_URL, accept(MediaType.APPLICATION_JSON), refreshTokenHandler::findByRefreshToken)
            .GET(COUNT_URL, accept(MediaType.APPLICATION_JSON), refreshTokenHandler::count)
            .DELETE(DELETEALL_URL, accept(MediaType.APPLICATION_JSON), refreshTokenHandler::deleteAll)
            .DELETE(DELETEBYID_URL, accept(MediaType.APPLICATION_JSON), refreshTokenHandler::deleteById)
            .build())
        .build();
  }
}
