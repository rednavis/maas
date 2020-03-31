package com.rednavis.mock.service;

import static com.rednavis.shared.dto.UserRole.ROLE_ADMIN;
import static com.rednavis.shared.dto.UserRole.ROLE_USER;

import com.rednavis.shared.dto.User;
import com.rednavis.shared.dto.UserRole;
import com.rednavis.webflux.service.MaasAuthPasswordRestService;
import com.rednavis.webflux.service.MaasDataUserRestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserMockService {

  private static final String ADMIN_EMAIL = "admin@admin.com";
  private static final String ADMIN_USERNAME = "admin";
  private static final String ADMIN_PASSWORD = "admin";
  private static final String USER_EMAIL = "user@user.com";
  private static final String USER_USERNAME = "user";
  private static final String USER_PASSWORD = "user";

  private final MaasAuthPasswordRestService maasAuthPasswordRestService;
  private final MaasDataUserRestService maasDataUserRestService;

  /**
   * mock.
   */
  public void mock() {
    maasDataUserRestService.deleteAll()
        .then(createAdmin())
        .map(admin -> maasDataUserRestService.insert(admin))
        .then(createUser())
        .map(user -> maasDataUserRestService.insert(user))
        .subscribe();
  }

  private Mono<User> createAdmin() {
    return maasAuthPasswordRestService.generatePasswordHash(ADMIN_PASSWORD)
        .map(passwordHash -> user(ADMIN_EMAIL, ADMIN_USERNAME, passwordHash, ROLE_ADMIN))
        .map(user -> {
          log.info("Create default admin [admin: {}]", user);
          return user;
        });
  }

  private Mono<User> createUser() {
    return maasAuthPasswordRestService.generatePasswordHash(USER_PASSWORD)
        .map(passwordHash -> user(USER_EMAIL, USER_USERNAME, passwordHash, ROLE_USER))
        .map(user -> {
          log.info("Create default user [user: {}]", user);
          return user;
        });
  }

  private User user(String userEmail, String userName, String passwordHash, UserRole userRole) {
    return User.builder()
        .firstName(User.Fields.firstName)
        .lastName(User.Fields.lastName)
        .email(userEmail)
        .userName(userName)
        .password(passwordHash)
        .role(userRole)
        .build();
  }
}
