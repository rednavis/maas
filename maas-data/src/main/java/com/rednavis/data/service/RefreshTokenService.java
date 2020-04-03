package com.rednavis.data.service;

import static com.rednavis.data.mapper.MapperProvider.REFRESH_TOKEN_MAPPER;

import com.rednavis.data.repository.RefreshTokenRepository;
import com.rednavis.data.util.OffsetBasedPageRequest;
import com.rednavis.shared.dto.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

  private final RefreshTokenRepository refreshTokenRepository;

  /**
   * insert.
   *
   * @param refreshToken refreshToken
   * @return
   */
  public Mono<RefreshToken> insert(RefreshToken refreshToken) {
    return Mono.defer(() -> Mono.just(refreshToken))
        .map(REFRESH_TOKEN_MAPPER::dtoToEntity)
        .flatMap(refreshTokenRepository::insert)
        .map(REFRESH_TOKEN_MAPPER::entityToDto);
  }

  /**
   * save.
   *
   * @param refreshToken refreshToken
   * @return
   */
  public Mono<RefreshToken> save(RefreshToken refreshToken) {
    return Mono.defer(() -> Mono.just(refreshToken))
        .map(REFRESH_TOKEN_MAPPER::dtoToEntity)
        .flatMap(refreshTokenRepository::save)
        .map(REFRESH_TOKEN_MAPPER::entityToDto);
  }

  /**
   * findAll.
   *
   * @param limit  limit
   * @param offset offset
   * @return
   */
  public Flux<RefreshToken> findAll(int limit, int offset) {
    Pageable pageable = new OffsetBasedPageRequest(limit, offset);
    return refreshTokenRepository.findAll(pageable)
        .map(REFRESH_TOKEN_MAPPER::entityToDto);
  }

  /**
   * findById.
   *
   * @param refreshTokenId refreshTokenId
   * @return
   */
  public Mono<RefreshToken> findById(String refreshTokenId) {
    return refreshTokenRepository.findById(refreshTokenId)
        .map(REFRESH_TOKEN_MAPPER::entityToDto);
  }

  /**
   * findByEmail.
   *
   * @param refreshToken refreshToken
   * @return
   */
  public Mono<RefreshToken> findByRefreshToken(String refreshToken) {
    return refreshTokenRepository.findByRefreshToken(refreshToken)
        .map(REFRESH_TOKEN_MAPPER::entityToDto);
  }

  /**
   * count.
   *
   * @return
   */
  public Mono<Long> count() {
    return refreshTokenRepository.count();
  }

  /**
   * deleteById.
   *
   * @param refreshTokenId refreshTokenId
   * @return
   */
  public Mono<Void> deleteById(String refreshTokenId) {
    return refreshTokenRepository.deleteById(refreshTokenId);
  }

  /**
   * deleteAll.
   *
   * @return
   */
  public Mono<Void> deleteAll() {
    return refreshTokenRepository.deleteAll();
  }
}
