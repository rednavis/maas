package com.rednavis.webflux.service;

import static com.rednavis.shared.util.RestUrlUtils.COUNT_URL;
import static com.rednavis.shared.util.RestUrlUtils.DELETEALL_URL;
import static com.rednavis.shared.util.RestUrlUtils.DELETEBYID_URL;
import static com.rednavis.shared.util.RestUrlUtils.FINDALL_URL;
import static com.rednavis.shared.util.RestUrlUtils.FINDBYID_URL;
import static com.rednavis.shared.util.RestUrlUtils.FINDBYREFRESHTOKEN_URL;
import static com.rednavis.shared.util.RestUrlUtils.INSERT_URL;
import static com.rednavis.shared.util.RestUrlUtils.LIMIT;
import static com.rednavis.shared.util.RestUrlUtils.OFFSET;
import static com.rednavis.shared.util.RestUrlUtils.REFRESH_TOKEN_URL;
import static com.rednavis.shared.util.RestUrlUtils.SAVE_URL;
import static com.rednavis.shared.util.RestUrlUtils.USER_URL;

import com.rednavis.shared.dto.RefreshToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MaasDataRefreshTokenRestService {

  private final WebClient.Builder dataWebClient;

  public MaasDataRefreshTokenRestService(@Qualifier("dataWebClient") WebClient.Builder dataWebClient) {
    this.dataWebClient = dataWebClient;
  }

  /**
   * insert.
   *
   * @param refreshToken refreshToken
   * @return
   */
  public Mono<RefreshToken> insert(RefreshToken refreshToken) {
    return dataWebClient.build()
        .post()
        .uri(uriBuilder -> uriBuilder.path(REFRESH_TOKEN_URL)
            .path(INSERT_URL)
            .build())
        .body(BodyInserters.fromValue(refreshToken))
        .retrieve()
        .bodyToMono(RefreshToken.class);
  }

  /**
   * save.
   *
   * @param refreshToken refreshToken
   * @return
   */
  public Mono<RefreshToken> save(RefreshToken refreshToken) {
    return dataWebClient.build()
        .put()
        .uri(uriBuilder -> uriBuilder.path(REFRESH_TOKEN_URL)
            .path(SAVE_URL)
            .build())
        .body(BodyInserters.fromValue(refreshToken))
        .retrieve()
        .bodyToMono(RefreshToken.class);
  }

  /**
   * findAll.
   *
   * @param limit  limit
   * @param offset offset
   * @return
   */
  public Flux<RefreshToken> findAll(int limit, int offset) {
    return dataWebClient.build()
        .get()
        .uri(uriBuilder -> uriBuilder.path(REFRESH_TOKEN_URL)
            .path(FINDALL_URL)
            .queryParam(LIMIT, limit)
            .queryParam(OFFSET, offset)
            .build())
        .retrieve()
        .bodyToFlux(RefreshToken.class);
  }

  /**
   * findById.
   *
   * @param refreshTokenId refreshTokenId
   * @return
   */
  public Mono<RefreshToken> findById(String refreshTokenId) {
    return dataWebClient.build()
        .get()
        .uri(uriBuilder -> uriBuilder.path(REFRESH_TOKEN_URL)
            .path(FINDBYID_URL)
            .build(refreshTokenId))
        .retrieve()
        .bodyToMono(RefreshToken.class);
  }

  /**
   * findByRefreshToken.
   *
   * @param refreshToken refreshToken
   * @return
   */
  public Mono<RefreshToken> findByRefreshToken(String refreshToken) {
    return dataWebClient.build()
        .get()
        .uri(uriBuilder -> uriBuilder.path(USER_URL)
            .path(FINDBYREFRESHTOKEN_URL)
            .build(refreshToken))
        .retrieve()
        .bodyToMono(RefreshToken.class);
  }

  /**
   * count.
   *
   * @return
   */
  public Mono<Long> count() {
    return dataWebClient.build()
        .get()
        .uri(uriBuilder -> uriBuilder.path(REFRESH_TOKEN_URL)
            .path(COUNT_URL)
            .build())
        .retrieve()
        .bodyToMono(Long.class);
  }

  /**
   * deleteAll.
   *
   * @return
   */
  public Mono<Void> deleteAll() {
    return dataWebClient.build()
        .delete()
        .uri(uriBuilder -> uriBuilder.path(REFRESH_TOKEN_URL)
            .path(DELETEALL_URL)
            .build())
        .retrieve()
        .bodyToMono(Void.class);
  }

  /**
   * deleteById.
   *
   * @param refreshTokenId refreshTokenId
   * @return
   */
  public Mono<Void> deleteById(String refreshTokenId) {
    return dataWebClient.build()
        .delete()
        .uri(uriBuilder -> uriBuilder.path(REFRESH_TOKEN_URL)
            .path(DELETEBYID_URL)
            .build(refreshTokenId))
        .retrieve()
        .bodyToMono(Void.class);
  }
}
