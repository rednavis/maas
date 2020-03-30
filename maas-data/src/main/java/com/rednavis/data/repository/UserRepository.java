package com.rednavis.data.repository;

import com.rednavis.data.entity.UserEntity;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends GlobalReactiveMongoRepository<UserEntity, String> {

  Mono<UserEntity> findByEmail(String email);

  Mono<UserEntity> findByUserName(String userName);
}