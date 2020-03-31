package com.rednavis.data.service;

import static com.rednavis.data.mapper.MapperProvider.USER_MAPPER;

import com.rednavis.data.repository.UserRepository;
import com.rednavis.data.util.OffsetBasedPageRequest;
import com.rednavis.shared.dto.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  /**
   * insert.
   *
   * @param user user
   * @return
   */
  public Mono<User> insert(User user) {
    return Mono.defer(() -> Mono.just(user))
        .map(USER_MAPPER::dtoToEntity)
        .flatMap(userRepository::insert)
        .map(USER_MAPPER::entityToDto);
  }

  /**
   * save.
   *
   * @param user user
   * @return
   */
  public Mono<User> save(User user) {
    return Mono.defer(() -> Mono.just(user))
        .map(USER_MAPPER::dtoToEntity)
        .flatMap(userRepository::save)
        .map(USER_MAPPER::entityToDto);
  }

  /**
   * findAll.
   *
   * @param limit  limit
   * @param offset offset
   * @return
   */
  public Flux<User> findAll(int limit, int offset) {
    Pageable pageable = new OffsetBasedPageRequest(limit, offset);
    return userRepository.findAll(pageable)
        .map(USER_MAPPER::entityToDto);
  }

  /**
   * findById.
   *
   * @param userId userId
   * @return
   */
  public Mono<User> findById(String userId) {
    return userRepository.findById(userId)
        .map(USER_MAPPER::entityToDto);
  }

  /**
   * findByEmail.
   *
   * @param email email
   * @return
   */
  public Mono<User> findByEmail(String email) {
    return userRepository.findByEmail(email)
        .map(USER_MAPPER::entityToDto);
  }

  /**
   * findByUserName.
   *
   * @param userName userName
   * @return
   */
  public Mono<User> findByUserName(String userName) {
    return userRepository.findByUserName(userName)
        .map(USER_MAPPER::entityToDto);
  }

  /**
   * count.
   *
   * @return
   */
  public Mono<Long> count() {
    return userRepository.count();
  }

  /**
   * deleteById.
   *
   * @param userId userId
   * @return
   */
  public Mono<Void> deleteById(String userId) {
    return userRepository.deleteById(userId);
  }

  /**
   * deleteAll.
   *
   * @return
   */
  public Mono<Void> deleteAll() {
    return userRepository.deleteAll();
  }
}
