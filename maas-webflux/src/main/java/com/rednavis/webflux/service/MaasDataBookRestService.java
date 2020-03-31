package com.rednavis.webflux.service;

import static com.rednavis.shared.util.RestUrlUtils.BOOK_URL;
import static com.rednavis.shared.util.RestUrlUtils.COUNT_URL;
import static com.rednavis.shared.util.RestUrlUtils.DELETEALL_URL;
import static com.rednavis.shared.util.RestUrlUtils.DELETEBYID_URL;
import static com.rednavis.shared.util.RestUrlUtils.FINDALL_URL;
import static com.rednavis.shared.util.RestUrlUtils.FINDBYID_URL;
import static com.rednavis.shared.util.RestUrlUtils.INSERT_URL;
import static com.rednavis.shared.util.RestUrlUtils.LIMIT;
import static com.rednavis.shared.util.RestUrlUtils.OFFSET;
import static com.rednavis.shared.util.RestUrlUtils.SAVE_URL;

import com.rednavis.shared.dto.Book;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MaasDataBookRestService {

  private final WebClient.Builder dataWebClient;

  public MaasDataBookRestService(@Qualifier("dataWebClient") WebClient.Builder dataWebClient) {
    this.dataWebClient = dataWebClient;
  }

  public Mono<Book> insert(Book book) {
    return dataWebClient.build()
        .post()
        .uri(uriBuilder -> uriBuilder.path(BOOK_URL)
            .path(INSERT_URL)
            .build())
        .body(BodyInserters.fromValue(book))
        .retrieve()
        .bodyToMono(Book.class);
  }

  public Mono<Book> save(Book book) {
    return dataWebClient.build()
        .put()
        .uri(uriBuilder -> uriBuilder.path(BOOK_URL)
            .path(SAVE_URL)
            .build())
        .body(BodyInserters.fromValue(book))
        .retrieve()
        .bodyToMono(Book.class);
  }

  public Flux<Book> findAll(int limit, int offset) {
    return dataWebClient.build()
        .get()
        .uri(uriBuilder -> uriBuilder.path(BOOK_URL)
            .path(FINDALL_URL)
            .queryParam(LIMIT, limit)
            .queryParam(OFFSET, offset)
            .build())
        .retrieve()
        .bodyToFlux(Book.class);
  }

  public Mono<Book> findById(String bookId) {
    return dataWebClient.build()
        .get()
        .uri(uriBuilder -> uriBuilder.path(BOOK_URL)
            .path(FINDBYID_URL)
            .build(bookId))
        .retrieve()
        .bodyToMono(Book.class);
  }

  public Mono<Long> count() {
    return dataWebClient.build()
        .get()
        .uri(uriBuilder -> uriBuilder.path(BOOK_URL)
            .path(COUNT_URL)
            .build())
        .retrieve()
        .bodyToMono(Long.class);
  }

  public Mono<Void> deleteAll() {
    return dataWebClient.build()
        .delete()
        .uri(uriBuilder -> uriBuilder.path(BOOK_URL)
            .path(DELETEALL_URL)
            .build())
        .retrieve()
        .bodyToMono(Void.class);
  }

  public Mono<Void> deleteById(String bookId) {
    return dataWebClient.build()
        .delete()
        .uri(uriBuilder -> uriBuilder.path(BOOK_URL)
            .path(DELETEBYID_URL)
            .build(bookId))
        .retrieve()
        .bodyToMono(Void.class);
  }
}
