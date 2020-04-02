package com.rednavis.webflux.service;

import static com.rednavis.shared.util.RestUrlUtils.GENERATE_PASSWORD_HASH_URL;
import static com.rednavis.shared.util.RestUrlUtils.PASSWORD_URL;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MaasAuthPasswordRestService {

  private final WebClient.Builder authWebClient;

  public MaasAuthPasswordRestService(@Qualifier("authWebClient") WebClient.Builder authWebClient) {
    this.authWebClient = authWebClient;
  }

  /**
   * generatePasswordHash.
   *
   * @param password password
   * @return
   */
  public Mono<String> generatePasswordHash(String password) {
    return authWebClient.build()
        .get()
        .uri(uriBuilder -> uriBuilder.path(PASSWORD_URL)
            .path(GENERATE_PASSWORD_HASH_URL)
            .build(password))
        .retrieve()
        .bodyToMono(String.class);
  }
}
