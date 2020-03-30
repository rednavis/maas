package com.rednavis.data.route;

import static com.rednavis.shared.util.RestUrlUtils.COUNT_URL;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.rednavis.data.MaasDataApplicationTest;
import com.rednavis.data.repository.UserRepository;
import com.rednavis.data.service.UserService;
import com.rednavis.shared.dto.User;
import com.rednavis.shared.dto.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class UserRouteTest extends MaasDataApplicationTest {

  private static final User NEW_USER1 = createUser(1);
  private static final User NEW_USER2 = createUser(2);

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserService userService;

  private static User createUser(int id) {
    return User.builder()
        .firstName(User.Fields.firstName + "_" + id)
        .lastName(User.Fields.lastName + "_" + id)
        .email(User.Fields.email + "_" + id)
        .userName(User.Fields.userName + "_" + id)
        .password(User.Fields.password + "_" + id)
        .role(UserRole.ROLE_ADMIN)
        .build();
  }

  @BeforeEach
  public void cleanUp() {
    Mono<Void> deleteAll = userRepository.deleteAll();
    StepVerifier
        .create(deleteAll)
        .verifyComplete();
  }

  @Test
  void insert() {
    getWebTestClient().post()
        .uri(USER_URL + INSERT_URL)
        .contentType(APPLICATION_JSON)
        .body(BodyInserters.fromValue(NEW_USER1))
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(User.class)
        .value(userResponse -> {
          assertNotNull(userResponse.getId());
          assertEquals(User.Fields.firstName + "_1", userResponse.getFirstName());
          assertEquals(User.Fields.lastName + "_1", userResponse.getLastName());
          assertEquals(User.Fields.email + "_1", userResponse.getEmail());
          assertEquals(User.Fields.userName + "_1", userResponse.getUserName());
          assertEquals(User.Fields.password + "_1", userResponse.getPassword());
          assertEquals(UserRole.ROLE_ADMIN, userResponse.getRole());
        });
  }

  @Test
  void save() {
    User existUser = userService.insert(NEW_USER1)
        .block();
    existUser.setFirstName("Test FirstName");
    existUser.setRole(UserRole.ROLE_USER);

    getWebTestClient().put()
        .uri(USER_URL + SAVE_URL)
        .contentType(APPLICATION_JSON)
        .body(BodyInserters.fromValue(existUser))
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(User.class)
        .value(userResponse -> {
          assertNotNull(userResponse.getId());
          assertEquals(UserRole.ROLE_USER, userResponse.getRole());
          assertEquals("Test FirstName", userResponse.getFirstName());
        });
  }

  @Test
  void findAll() {
    userService.insert(NEW_USER1)
        .block();

    getWebTestClient().get()
        .uri(uriBuilder -> uriBuilder.path(USER_URL + FINDALL_URL)
            .queryParam(LIMIT, 5)
            .queryParam(OFFSET, 0)
            .build())
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(User.class)
        .value(userResponse -> assertEquals(1, userResponse.size()));

    userService.insert(NEW_USER2)
        .block();

    getWebTestClient().get()
        .uri(uriBuilder -> uriBuilder.path(USER_URL + FINDALL_URL)
            .queryParam(LIMIT, 5)
            .queryParam(OFFSET, 0)
            .build())
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(User.class)
        .value(userResponse -> assertEquals(2, userResponse.size()));
  }

  @Test
  void findById() {
    String userId = userService.insert(NEW_USER1)
        .block()
        .getId();

    getWebTestClient().get()
        .uri(uriBuilder -> uriBuilder.path(USER_URL + FINDBYID_URL)
            .build(userId))
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(User.class)
        .value(userResponse -> {
          assertEquals(userId, userResponse.getId());
          assertEquals(User.Fields.firstName + "_1", userResponse.getFirstName());
          assertEquals(User.Fields.lastName + "_1", userResponse.getLastName());
          assertEquals(User.Fields.email + "_1", userResponse.getEmail());
          assertEquals(User.Fields.userName + "_1", userResponse.getUserName());
          assertEquals(User.Fields.password + "_1", userResponse.getPassword());
          assertEquals(UserRole.ROLE_ADMIN, userResponse.getRole());
        });
  }

  @Test
  void findByEmail() {
    String email = userService.insert(NEW_USER1)
        .block()
        .getEmail();

    getWebTestClient().get()
        .uri(uriBuilder -> uriBuilder.path(USER_URL + FINDBYEMAIL_URL)
            .build(email))
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(User.class)
        .value(userResponse -> {
          assertEquals(email, userResponse.getEmail());
          assertEquals(User.Fields.firstName + "_1", userResponse.getFirstName());
          assertEquals(User.Fields.lastName + "_1", userResponse.getLastName());
          assertEquals(User.Fields.userName + "_1", userResponse.getUserName());
          assertEquals(User.Fields.password + "_1", userResponse.getPassword());
          assertEquals(UserRole.ROLE_ADMIN, userResponse.getRole());
        });
  }

  @Test
  void findByUserName() {
    String userName = userService.insert(NEW_USER1)
        .block()
        .getUserName();

    getWebTestClient().get()
        .uri(uriBuilder -> uriBuilder.path(USER_URL + FINDBYUSERNAME_URL)
            .build(userName))
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(User.class)
        .value(userResponse -> {
          assertEquals(userName, userResponse.getUserName());
          assertEquals(User.Fields.firstName + "_1", userResponse.getFirstName());
          assertEquals(User.Fields.lastName + "_1", userResponse.getLastName());
          assertEquals(User.Fields.email + "_1", userResponse.getEmail());
          assertEquals(User.Fields.password + "_1", userResponse.getPassword());
          assertEquals(UserRole.ROLE_ADMIN, userResponse.getRole());
        });
  }

  @Test
  void count() {
    getWebTestClient().get()
        .uri(USER_URL + COUNT_URL)
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Long.class)
        .value(count -> assertEquals(0, count));

    userService.insert(NEW_USER1)
        .block();

    getWebTestClient().get()
        .uri(USER_URL + COUNT_URL)
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Long.class)
        .value(count -> assertEquals(1, count));
  }

  @Test
  void deleteById() {
    String userId = userService.insert(NEW_USER1)
        .block()
        .getId();

    getWebTestClient().delete()
        .uri(uriBuilder -> uriBuilder.path(USER_URL + DELETEBYID_URL)
            .build(userId))
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk();

    getWebTestClient().get()
        .uri(USER_URL + COUNT_URL)
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Long.class)
        .value(count -> assertEquals(0, count));
  }
}