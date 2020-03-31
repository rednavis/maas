package com.rednavis.auth.handler;

import static com.rednavis.webflux.util.RouteUtils.createOkResponse;

import com.rednavis.auth.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class PasswordHandler {

  @Autowired
  private PasswordService passwordService;

  /**
   * generatePasswordHash.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> generatePasswordHash(ServerRequest request) {
    return Mono.defer(() -> Mono.just(request.pathVariable("password"))
        .flatMap(password -> {
          String passwordHash = passwordService.generatePasswordHash(password);
          return createOkResponse(Mono.defer(() -> Mono.just(passwordHash)), String.class);
        }));
  }
}
