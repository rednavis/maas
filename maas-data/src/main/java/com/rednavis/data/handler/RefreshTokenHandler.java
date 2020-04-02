package com.rednavis.data.handler;

import static com.rednavis.shared.util.RestUrlUtils.LIMIT;
import static com.rednavis.shared.util.RestUrlUtils.OFFSET;
import static com.rednavis.webflux.util.RouteUtils.createBadRequest;
import static com.rednavis.webflux.util.RouteUtils.createOkResponse;

import com.rednavis.data.service.RefreshTokenService;
import com.rednavis.shared.dto.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RefreshTokenHandler {

  private final RefreshTokenService refreshTokenService;

  /**
   * insert.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> insert(ServerRequest request) {
    return request.bodyToMono(RefreshToken.class)
        .flatMap(refreshToken -> createOkResponse(refreshTokenService.insert(refreshToken), RefreshToken.class))
        .switchIfEmpty(createBadRequest("RefreshToken cann't be empty"));
  }

  /**
   * save.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> save(ServerRequest request) {
    return request.bodyToMono(RefreshToken.class)
        .flatMap(refreshToken -> createOkResponse(refreshTokenService.save(refreshToken), RefreshToken.class))
        .switchIfEmpty(createBadRequest("RefreshToken cann't be empty"));
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
    return createOkResponse(refreshTokenService.findAll(limit, offset), RefreshToken.class);
  }

  /**
   * findById.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> findById(ServerRequest request) {
    return Mono.defer(() -> Mono.just(request.pathVariable("id"))
        .flatMap(refreshTokenId -> createOkResponse(refreshTokenService.findById(refreshTokenId), RefreshToken.class)));
  }

  /**
   * findByEmail.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> findByRefreshToken(ServerRequest request) {
    return Mono.defer(() -> Mono.just(request.pathVariable("refreshToken"))
        .flatMap(refreshToken -> createOkResponse(refreshTokenService.findByRefreshToken(refreshToken), RefreshToken.class)))
        .switchIfEmpty(createBadRequest("RefreshToken cann't be empty"));
  }

  /**
   * count.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> count(ServerRequest request) {
    return createOkResponse(refreshTokenService.count(), Long.class);
  }

  /**
   * deleteAll.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> deleteAll(ServerRequest request) {
    return createOkResponse(refreshTokenService.deleteAll(), Void.class);
  }

  /**
   * deleteById.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> deleteById(ServerRequest request) {
    return Mono.defer(() -> Mono.just(request.pathVariable("id"))
        .flatMap(refreshTokenId -> createOkResponse(refreshTokenService.deleteById(refreshTokenId), Void.class)))
        .switchIfEmpty(createBadRequest("ID cann't be empty"));
  }
}
