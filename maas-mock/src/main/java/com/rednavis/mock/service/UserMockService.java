package com.rednavis.mock.service;

import static com.rednavis.shared.dto.UserRole.ROLE_ADMIN;
import static com.rednavis.shared.dto.UserRole.ROLE_USER;
import static com.rednavis.shared.util.RestUrlUtils.DELETEALL_URL;
import static com.rednavis.shared.util.RestUrlUtils.GENERATE_PASSWORD_HASH_URL;
import static com.rednavis.shared.util.RestUrlUtils.INSERT_URL;
import static com.rednavis.shared.util.RestUrlUtils.PASSWORD_URL;
import static com.rednavis.shared.util.RestUrlUtils.USER_URL;

import com.rednavis.shared.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class UserMockService {

  private static final String ADMIN_EMAIL = "admin@admin.com";
  private static final String ADMIN_USERNAME = "admin";
  private static final String ADMIN_PASSWORD = "admin";
  private static final String USER_EMAIL = "user@user.com";
  private static final String USER_USERNAME = "user";
  private static final String USER_PASSWORD = "user";

  private final WebClient.Builder dataWebClient;
  private final WebClient.Builder authWebClient;

  public UserMockService(@Qualifier("dataWebClient") WebClient.Builder dataWebClient,
      @Qualifier("authWebClient") WebClient.Builder authWebClient) {
    this.dataWebClient = dataWebClient;
    this.authWebClient = authWebClient;
  }

  /**
   * mock.
   */
  public void mock() {
    clearCollection()
        .then(insert(createAdmin()))
        .then(insert(createUser()))
        .subscribe();
  }

  private Mono<Void> clearCollection() {
    return dataWebClient.build()
        .delete()
        .uri(uriBuilder -> uriBuilder.path(USER_URL)
            .path(DELETEALL_URL)
            .build())
        .retrieve()
        .bodyToMono(Void.class);
  }

  private Mono<User> insert(Mono<User> userMono) {
    return dataWebClient.build()
        .post()
        .uri(uriBuilder -> uriBuilder.path(USER_URL)
            .path(INSERT_URL)
            .build())
        .body(userMono, User.class)
        .retrieve()
        .bodyToMono(User.class);
  }

  private Mono<User> createAdmin() {
    Mono<String> passwordHashMono = authWebClient.build()
        .get()
        .uri(uriBuilder -> uriBuilder.path(PASSWORD_URL)
            .path(GENERATE_PASSWORD_HASH_URL)
            .build(ADMIN_PASSWORD))
        .retrieve()
        .bodyToMono(String.class);
    return passwordHashMono.map(passwordHash -> User.builder()
        .firstName(User.Fields.firstName)
        .lastName(User.Fields.lastName)
        .email(ADMIN_EMAIL)
        .userName(ADMIN_USERNAME)
        .password(passwordHash)
        .role(ROLE_ADMIN)
        .build())
        .map(user -> {
          log.info("Create default admin [admin: {}]", user);
          return user;
        });
  }

  private Mono<User> createUser() {
    Mono<String> passwordHashMono = authWebClient.build()
        .get()
        .uri(uriBuilder -> uriBuilder.path(PASSWORD_URL)
            .path(GENERATE_PASSWORD_HASH_URL)
            .build(USER_PASSWORD))
        .retrieve()
        .bodyToMono(String.class);
    return passwordHashMono.map(passwordHash -> User.builder()
        .firstName(User.Fields.firstName)
        .lastName(User.Fields.lastName)
        .email(USER_EMAIL)
        .userName(USER_USERNAME)
        .password(passwordHash)
        .role(ROLE_USER)
        .build())
        .map(user -> {
          log.info("Create default user [user: {}]", user);
          return user;
        });
  }
}
