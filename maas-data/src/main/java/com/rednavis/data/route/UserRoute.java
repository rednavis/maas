package com.rednavis.data.route;

import static com.rednavis.shared.util.RestUrlUtils.COUNT_URL;
import static com.rednavis.shared.util.RestUrlUtils.DELETEBYID_URL;
import static com.rednavis.shared.util.RestUrlUtils.FINDALL_URL;
import static com.rednavis.shared.util.RestUrlUtils.FINDBYEMAIL_URL;
import static com.rednavis.shared.util.RestUrlUtils.FINDBYID_URL;
import static com.rednavis.shared.util.RestUrlUtils.FINDBYUSERNAME_URL;
import static com.rednavis.shared.util.RestUrlUtils.INSERT_URL;
import static com.rednavis.shared.util.RestUrlUtils.SAVE_URL;
import static com.rednavis.shared.util.RestUrlUtils.USER_URL;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import com.rednavis.data.handler.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Configuration
public class UserRoute {

  @Autowired
  private UserHandler userHandler;

  /**
   * userRouteFunction.
   *
   * @return
   */
  @Bean
  public RouterFunction userRouteFunction() {
    return RouterFunctions.route()
        .path(USER_URL, builder -> builder
            .POST(INSERT_URL, accept(MediaType.APPLICATION_JSON), userHandler::insert)
            .PUT(SAVE_URL, accept(MediaType.APPLICATION_JSON), userHandler::save)
            .GET(FINDALL_URL, accept(MediaType.APPLICATION_JSON), userHandler::findAll)
            .GET(FINDBYID_URL, accept(MediaType.APPLICATION_JSON), userHandler::findById)
            .GET(FINDBYEMAIL_URL, accept(MediaType.APPLICATION_JSON), userHandler::findByEmail)
            .GET(FINDBYUSERNAME_URL, accept(MediaType.APPLICATION_JSON), userHandler::findByUserName)
            .GET(COUNT_URL, accept(MediaType.APPLICATION_JSON), userHandler::count)
            .DELETE(DELETEBYID_URL, accept(MediaType.APPLICATION_JSON), userHandler::deleteById)
            .build())
        .build();
  }
}