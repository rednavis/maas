package com.rednavis.data.util;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import lombok.experimental.UtilityClass;
import org.reactivestreams.Publisher;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@UtilityClass
public class RouteUtil {

  /**
   * createOkResponse.
   *
   * @param publisher    publisher
   * @param elementClass elementClass
   * @param <T>          elementClass
   * @param <P>          publisher
   * @return
   */
  public static <T, P extends Publisher<T>> Mono<ServerResponse> createOkResponse(P publisher, Class<T> elementClass) {
    return ServerResponse.ok()
        .contentType(APPLICATION_JSON)
        .body(publisher, elementClass);
  }

  /**
   * createBadRequest.
   *
   * @param errMessage errMessage
   * @return
   */
  public static Mono<ServerResponse> createBadRequest(String errMessage) {
    return ServerResponse.badRequest()
        .contentType(APPLICATION_JSON)
        .bodyValue(errMessage);
  }
}
