package com.rednavis.data.route;

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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.time.Instant;
import com.rednavis.data.MaasDataApplicationTest;
import com.rednavis.data.service.RefreshTokenService;
import com.rednavis.shared.dto.RefreshToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class RefreshTokenRouteTest extends MaasDataApplicationTest {

  private static final RefreshToken NEW_REFRESH_TOKEN1 = createRefreshToken(1);
  private static final RefreshToken NEW_REFRESH_TOKEN2 = createRefreshToken(2);

  @Autowired
  private RefreshTokenService refreshTokenService;

  private static RefreshToken createRefreshToken(int id) {
    return RefreshToken.builder()
        .refreshToken(RefreshToken.Fields.refreshToken + "_" + id)
        .userId(RefreshToken.Fields.userId + "_" + id)
        .expiration(Instant.now())
        .build();
  }

  /**
   * cleanUp.
   */
  @BeforeEach
  public void cleanUp() {
    Mono<Void> deleteAll = refreshTokenService.deleteAll();
    StepVerifier
        .create(deleteAll)
        .verifyComplete();
  }

  @Test
  void insert() {
    getWebTestClient().post()
        .uri(REFRESH_TOKEN_URL + INSERT_URL)
        .contentType(APPLICATION_JSON)
        .body(BodyInserters.fromValue(NEW_REFRESH_TOKEN1))
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(RefreshToken.class)
        .value(refreshTokenResponse -> {
          assertNotNull(refreshTokenResponse.getId());
          assertEquals(RefreshToken.Fields.refreshToken + "_1", refreshTokenResponse.getRefreshToken());
          assertEquals(RefreshToken.Fields.userId + "_1", refreshTokenResponse.getUserId());
        });
  }

  @Test
  void save() {
    RefreshToken existRefreshToken = refreshTokenService.insert(NEW_REFRESH_TOKEN1)
        .block();
    existRefreshToken.setRefreshToken("Test RefreshToken");
    existRefreshToken.setUserId("Test UserId");

    getWebTestClient().put()
        .uri(REFRESH_TOKEN_URL + SAVE_URL)
        .contentType(APPLICATION_JSON)
        .body(BodyInserters.fromValue(existRefreshToken))
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(RefreshToken.class)
        .value(refreshTokenResponse -> {
          assertNotNull(refreshTokenResponse.getId());
          assertEquals("Test RefreshToken", refreshTokenResponse.getRefreshToken());
          assertEquals("Test UserId", refreshTokenResponse.getUserId());
        });
  }

  @Test
  void findAll() {
    refreshTokenService.insert(NEW_REFRESH_TOKEN1)
        .block();

    getWebTestClient().get()
        .uri(uriBuilder -> uriBuilder.path(REFRESH_TOKEN_URL + FINDALL_URL)
            .queryParam(LIMIT, 5)
            .queryParam(OFFSET, 0)
            .build())
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(RefreshToken.class)
        .value(refreshTokenResponse -> assertEquals(1, refreshTokenResponse.size()));

    refreshTokenService.insert(NEW_REFRESH_TOKEN2)
        .block();

    getWebTestClient().get()
        .uri(uriBuilder -> uriBuilder.path(REFRESH_TOKEN_URL + FINDALL_URL)
            .queryParam(LIMIT, 5)
            .queryParam(OFFSET, 0)
            .build())
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(RefreshToken.class)
        .value(refreshTokenResponse -> assertEquals(2, refreshTokenResponse.size()));
  }

  @Test
  void findById() {
    String refreshTokenId = refreshTokenService.insert(NEW_REFRESH_TOKEN1)
        .block()
        .getId();

    getWebTestClient().get()
        .uri(uriBuilder -> uriBuilder.path(REFRESH_TOKEN_URL + FINDBYID_URL)
            .build(refreshTokenId))
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(RefreshToken.class)
        .value(refreshTokenResponse -> {
          assertEquals(refreshTokenId, refreshTokenResponse.getId());
          assertEquals(RefreshToken.Fields.refreshToken + "_1", refreshTokenResponse.getRefreshToken());
          assertEquals(RefreshToken.Fields.userId + "_1", refreshTokenResponse.getUserId());
        });
  }

  @Test
  void findByRefreshToken() {
    String refreshToken = refreshTokenService.insert(NEW_REFRESH_TOKEN1)
        .block()
        .getRefreshToken();

    getWebTestClient().get()
        .uri(uriBuilder -> uriBuilder.path(USER_URL + FINDBYREFRESHTOKEN_URL)
            .build(refreshToken))
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(RefreshToken.class)
        .value(refreshTokenResponse -> {
          assertEquals(refreshToken, refreshTokenResponse.getRefreshToken());
          assertEquals(RefreshToken.Fields.userId + "_1", refreshTokenResponse.getUserId());
        });
  }

  @Test
  void count() {
    getWebTestClient().get()
        .uri(REFRESH_TOKEN_URL + COUNT_URL)
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Long.class)
        .value(count -> assertEquals(0, count));

    refreshTokenService.insert(NEW_REFRESH_TOKEN1)
        .block();

    getWebTestClient().get()
        .uri(REFRESH_TOKEN_URL + COUNT_URL)
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Long.class)
        .value(count -> assertEquals(1, count));
  }

  @Test
  void deleteAll() {
    refreshTokenService.insert(NEW_REFRESH_TOKEN1)
        .block();

    getWebTestClient().delete()
        .uri(REFRESH_TOKEN_URL + DELETEALL_URL)
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk();

    getWebTestClient().get()
        .uri(REFRESH_TOKEN_URL + COUNT_URL)
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Long.class)
        .value(count -> assertEquals(0, count));
  }

  @Test
  void deleteById() {
    String refreshTokenId = refreshTokenService.insert(NEW_REFRESH_TOKEN1)
        .block()
        .getId();

    getWebTestClient().delete()
        .uri(uriBuilder -> uriBuilder.path(REFRESH_TOKEN_URL + DELETEBYID_URL)
            .build(refreshTokenId))
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk();

    getWebTestClient().get()
        .uri(REFRESH_TOKEN_URL + COUNT_URL)
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Long.class)
        .value(count -> assertEquals(0, count));
  }
}