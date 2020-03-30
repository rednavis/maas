package com.rednavis.data.service;

import com.rednavis.shared.dto.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

  Mono<User> insert(User user);

  Mono<User> save(User user);

  Flux<User> findAll(int limit, int offset);

  Mono<User> findById(String userId);

  Mono<User> findByEmail(String email);

  Mono<User> findByUserName(String userName);

  Mono<Long> count();

  Mono<Void> deleteById(String userId);
}
