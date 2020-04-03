package com.rednavis.data.service;

import static com.rednavis.data.mapper.MapperProvider.BOOK_MAPPER;

import com.rednavis.data.repository.BookRepository;
import com.rednavis.data.util.OffsetBasedPageRequest;
import com.rednavis.shared.dto.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;

  /**
   * insert.
   *
   * @param book book
   * @return
   */
  public Mono<Book> insert(Book book) {
    return Mono.defer(() -> Mono.just(book))
        .map(BOOK_MAPPER::dtoToEntity)
        .flatMap(bookRepository::insert)
        .map(BOOK_MAPPER::entityToDto);
  }

  /**
   * save.
   *
   * @param book book
   * @return
   */
  public Mono<Book> save(Book book) {
    return Mono.defer(() -> Mono.just(book))
        .map(BOOK_MAPPER::dtoToEntity)
        .flatMap(bookRepository::save)
        .map(BOOK_MAPPER::entityToDto);
  }

  /**
   * findAll.
   *
   * @param limit  limit
   * @param offset offset
   * @return
   */
  public Flux<Book> findAll(int limit, int offset) {
    Pageable pageable = new OffsetBasedPageRequest(limit, offset);
    return bookRepository.findAll(pageable)
        .map(BOOK_MAPPER::entityToDto);
  }

  /**
   * findById.
   *
   * @param bookId bookId
   * @return
   */
  public Mono<Book> findById(String bookId) {
    return bookRepository.findById(bookId)
        .map(BOOK_MAPPER::entityToDto);
  }

  /**
   * count.
   *
   * @return
   */
  public Mono<Long> count() {
    return bookRepository.count();
  }

  /**
   * deleteById.
   *
   * @param bookId bookId
   * @return
   */
  public Mono<Void> deleteById(String bookId) {
    return bookRepository.deleteById(bookId);
  }

  /**
   * deleteAll.
   *
   * @return
   */
  public Mono<Void> deleteAll() {
    return bookRepository.deleteAll();
  }
}
