package com.rednavis.data.handler;

import static com.rednavis.shared.util.RestUrlUtils.LIMIT;
import static com.rednavis.shared.util.RestUrlUtils.OFFSET;
import static com.rednavis.webflux.util.RouteUtils.createBadRequest;
import static com.rednavis.webflux.util.RouteUtils.createOkResponse;

import com.rednavis.data.service.UserService;
import com.rednavis.shared.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {

  private final UserService userService;

  /**
   * insert.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> insert(ServerRequest request) {
    return request.bodyToMono(User.class)
        .flatMap(user -> createOkResponse(userService.insert(user), User.class))
        .switchIfEmpty(createBadRequest("User cann't be empty"));
  }

  /**
   * save.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> save(ServerRequest request) {
    return request.bodyToMono(User.class)
        .flatMap(user -> createOkResponse(userService.save(user), User.class))
        .switchIfEmpty(createBadRequest("User cann't be empty"));
  }

  /**
   * findAll.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> findAll(ServerRequest request) {
    int limit = Integer.parseInt(request.queryParam(LIMIT).orElse("0"));
    int offset = Integer.parseInt(request.queryParam(OFFSET).orElse("0"));
    return createOkResponse(userService.findAll(limit, offset), User.class);
  }

  /**
   * findById.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> findById(ServerRequest request) {
    return Mono.defer(() -> Mono.just(request.pathVariable("id"))
        .flatMap(userId -> createOkResponse(userService.findById(userId), User.class)))
        .switchIfEmpty(createBadRequest("ID cann't be empty"));
  }

  /**
   * findByEmail.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> findByEmail(ServerRequest request) {
    return Mono.defer(() -> Mono.just(request.pathVariable("email"))
        .flatMap(email -> createOkResponse(userService.findByEmail(email), User.class)))
        .switchIfEmpty(createBadRequest("Email cann't be empty"));
  }

  /**
   * findByUserName.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> findByUserName(ServerRequest request) {
    return Mono.defer(() -> Mono.just(request.pathVariable("userName"))
        .flatMap(userName -> createOkResponse(userService.findByUserName(userName), User.class)))
        .switchIfEmpty(createBadRequest("UserName cann't be empty"));
  }

  /**
   * count.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> count(ServerRequest request) {
    return createOkResponse(userService.count(), Long.class);
  }

  /**
   * deleteAll.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> deleteAll(ServerRequest request) {
    return createOkResponse(userService.deleteAll(), Void.class);
  }

  /**
   * deleteById.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> deleteById(ServerRequest request) {
    return Mono.defer(() -> Mono.just(request.pathVariable("id"))
        .flatMap(userId -> createOkResponse(userService.deleteById(userId), Void.class)))
        .switchIfEmpty(createBadRequest("ID cann't be empty"));
  }
}
