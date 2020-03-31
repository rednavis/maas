package com.rednavis.webflux.util;

import lombok.experimental.UtilityClass;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@UtilityClass
public class RouteUtils {

  /**
   * createOkResponse.
   *
   * @param publisher    publisher
   * @param elementClass elementClass
   * @param <T>          elementClass
   * @param <P>          publisher
   * @return
   */
  public <T, P extends Publisher<T>> Mono<ServerResponse> createOkResponse(P publisher, Class<T> elementClass) {
    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(publisher, elementClass);
  }

  /**
   * createBadRequest.
   *
   * @param errMessage errMessage
   * @return
   */
  public Mono<ServerResponse> createBadRequest(String errMessage) {
    return ServerResponse.badRequest()
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(errMessage);
  }
}
