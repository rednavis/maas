package com.rednavis.webflux.service;

import static com.rednavis.shared.util.RestUrlUtils.COUNT_URL;
import static com.rednavis.shared.util.RestUrlUtils.DELETEALL_URL;
import static com.rednavis.shared.util.RestUrlUtils.DELETEBYID_URL;
import static com.rednavis.shared.util.RestUrlUtils.FINDALL_URL;
import static com.rednavis.shared.util.RestUrlUtils.FINDBYEMAIL_URL;
import static com.rednavis.shared.util.RestUrlUtils.FINDBYID_URL;
import static com.rednavis.shared.util.RestUrlUtils.FINDBYUSERNAME_URL;
import static com.rednavis.shared.util.RestUrlUtils.INSERT_URL;
import static com.rednavis.shared.util.RestUrlUtils.LIMIT;
import static com.rednavis.shared.util.RestUrlUtils.OFFSET;
import static com.rednavis.shared.util.RestUrlUtils.SAVE_URL;
import static com.rednavis.shared.util.RestUrlUtils.USER_URL;

import com.rednavis.shared.dto.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MaasDataUserRestService {

  private final WebClient.Builder dataWebClient;

  public MaasDataUserRestService(@Qualifier("dataWebClient") WebClient.Builder dataWebClient) {
    this.dataWebClient = dataWebClient;
  }

  public Mono<User> insert(User user) {
    return dataWebClient.build()
        .post()
        .uri(uriBuilder -> uriBuilder.path(USER_URL)
            .path(INSERT_URL)
            .build())
        .body(BodyInserters.fromValue(user))
        .retrieve()
        .bodyToMono(User.class);
  }

  public Mono<User> save(User user) {
    return dataWebClient.build()
        .put()
        .uri(uriBuilder -> uriBuilder.path(USER_URL)
            .path(SAVE_URL)
            .build())
        .body(BodyInserters.fromValue(user))
        .retrieve()
        .bodyToMono(User.class);
  }

  public Flux<User> findAll(int limit, int offset) {
    return dataWebClient.build()
        .get()
        .uri(uriBuilder -> uriBuilder.path(USER_URL)
            .path(FINDALL_URL)
            .queryParam(LIMIT, limit)
            .queryParam(OFFSET, offset)
            .build())
        .retrieve()
        .bodyToFlux(User.class);
  }

  public Mono<User> findById(String userId) {
    return dataWebClient.build()
        .get()
        .uri(uriBuilder -> uriBuilder.path(USER_URL)
            .path(FINDBYID_URL)
            .build(userId))
        .retrieve()
        .bodyToMono(User.class);
  }

  public Mono<User> findByEmail(String email) {
    return dataWebClient.build()
        .get()
        .uri(uriBuilder -> uriBuilder.path(USER_URL)
            .path(FINDBYEMAIL_URL)
            .build(email))
        .retrieve()
        .bodyToMono(User.class);
  }

  public Mono<User> findByUserName(String userName) {
    return dataWebClient.build()
        .get()
        .uri(uriBuilder -> uriBuilder.path(USER_URL)
            .path(FINDBYUSERNAME_URL)
            .build(userName))
        .retrieve()
        .bodyToMono(User.class);
  }

  public Mono<Long> count() {
    return dataWebClient.build()
        .get()
        .uri(uriBuilder -> uriBuilder.path(USER_URL)
            .path(COUNT_URL)
            .build())
        .retrieve()
        .bodyToMono(Long.class);
  }

  public Mono<Void> deleteAll() {
    return dataWebClient.build()
        .delete()
        .uri(uriBuilder -> uriBuilder.path(USER_URL)
            .path(DELETEALL_URL)
            .build())
        .retrieve()
        .bodyToMono(Void.class);
  }

  public Mono<Void> deleteById(String userId) {
    return dataWebClient.build()
        .delete()
        .uri(uriBuilder -> uriBuilder.path(USER_URL)
            .path(DELETEBYID_URL)
            .build(userId))
        .retrieve()
        .bodyToMono(Void.class);
  }
}
