package com.rednavis.data.repository;

import com.rednavis.data.entity.RefreshTokenEntity;
import reactor.core.publisher.Mono;

public interface RefreshTokenRepository extends GlobalRepository<RefreshTokenEntity, String> {

  Mono<RefreshTokenEntity> findByRefreshToken(String refreshToken);
}
