package com.rednavis.data.handler;

import static com.rednavis.shared.util.RestUrlUtils.LIMIT;
import static com.rednavis.shared.util.RestUrlUtils.OFFSET;
import static com.rednavis.webflux.util.RouteUtils.createBadRequest;
import static com.rednavis.webflux.util.RouteUtils.createOkResponse;

import com.rednavis.data.service.BookService;
import com.rednavis.shared.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class BookHandler {

  @Autowired
  private BookService bookService;

  /**
   * insert.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> insert(ServerRequest request) {
    return request.bodyToMono(Book.class)
        .flatMap(book -> createOkResponse(bookService.insert(book), Book.class))
        .switchIfEmpty(createBadRequest("Book cann't be empty"));
  }

  /**
   * save.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> save(ServerRequest request) {
    return request.bodyToMono(Book.class)
        .flatMap(book -> createOkResponse(bookService.save(book), Book.class))
        .switchIfEmpty(createBadRequest("Book cann't be empty"));
  }

  /**
   * findAll.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> findAll(ServerRequest request) {
    int limit = Integer.parseInt(request.queryParam(LIMIT).orElse("0"));
    int offset = Integer.parseInt(request.queryParam(OFFSET).orElse("0"));
    return createOkResponse(bookService.findAll(limit, offset), Book.class);
  }

  /**
   * findById.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> findById(ServerRequest request) {
    return Mono.defer(() -> Mono.just(request.pathVariable("id"))
        .flatMap(bookId -> createOkResponse(bookService.findById(bookId), Book.class)));
  }

  /**
   * count.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> count(ServerRequest request) {
    return createOkResponse(bookService.count(), Long.class);
  }

  /**
   * deleteAll.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> deleteAll(ServerRequest request) {
    return createOkResponse(bookService.deleteAll(), Void.class);
  }

  /**
   * deleteById.
   *
   * @param request request
   * @return
   */
  public Mono<ServerResponse> deleteById(ServerRequest request) {
    return Mono.defer(() -> Mono.just(request.pathVariable("id"))
        .flatMap(bookId -> createOkResponse(bookService.deleteById(bookId), Void.class)))
        .switchIfEmpty(createBadRequest("ID cann't be empty"));
  }
}
