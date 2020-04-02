package com.rednavis.data.route;

import static com.rednavis.shared.util.RestUrlUtils.BOOK_URL;
import static com.rednavis.shared.util.RestUrlUtils.COUNT_URL;
import static com.rednavis.shared.util.RestUrlUtils.DELETEALL_URL;
import static com.rednavis.shared.util.RestUrlUtils.DELETEBYID_URL;
import static com.rednavis.shared.util.RestUrlUtils.FINDALL_URL;
import static com.rednavis.shared.util.RestUrlUtils.FINDBYID_URL;
import static com.rednavis.shared.util.RestUrlUtils.INSERT_URL;
import static com.rednavis.shared.util.RestUrlUtils.SAVE_URL;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import com.rednavis.data.handler.BookHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Configuration
@RequiredArgsConstructor
public class BookRoute {

  private final BookHandler bookHandler;

  /**
   * bookRouteFunction.
   *
   * @return
   */
  @Bean
  public RouterFunction bookRouteFunction() {
    return RouterFunctions.route()
        .path(BOOK_URL, builder -> builder
            .POST(INSERT_URL, accept(MediaType.APPLICATION_JSON), bookHandler::insert)
            .PUT(SAVE_URL, accept(MediaType.APPLICATION_JSON), bookHandler::save)
            .GET(FINDALL_URL, accept(MediaType.APPLICATION_JSON), bookHandler::findAll)
            .GET(FINDBYID_URL, accept(MediaType.APPLICATION_JSON), bookHandler::findById)
            .GET(COUNT_URL, accept(MediaType.APPLICATION_JSON), bookHandler::count)
            .DELETE(DELETEALL_URL, accept(MediaType.APPLICATION_JSON), bookHandler::deleteAll)
            .DELETE(DELETEBYID_URL, accept(MediaType.APPLICATION_JSON), bookHandler::deleteById)
            .build())
        .build();
  }
}
