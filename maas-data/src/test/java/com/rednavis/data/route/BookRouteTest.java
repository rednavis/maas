package com.rednavis.data.route;

import static com.rednavis.shared.util.RestUrlUtils.BOOK_URL;
import static com.rednavis.shared.util.RestUrlUtils.COUNT_URL;
import static com.rednavis.shared.util.RestUrlUtils.DELETEBYID_URL;
import static com.rednavis.shared.util.RestUrlUtils.FINDALL_URL;
import static com.rednavis.shared.util.RestUrlUtils.FINDBYID_URL;
import static com.rednavis.shared.util.RestUrlUtils.INSERT_URL;
import static com.rednavis.shared.util.RestUrlUtils.LIMIT;
import static com.rednavis.shared.util.RestUrlUtils.OFFSET;
import static com.rednavis.shared.util.RestUrlUtils.SAVE_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.rednavis.data.MaasDataApplicationTest;
import com.rednavis.data.repository.BookRepository;
import com.rednavis.data.service.BookService;
import com.rednavis.shared.dto.Book;
import com.rednavis.shared.dto.BookStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class BookRouteTest extends MaasDataApplicationTest {

  private static final Book NEW_BOOK1 = createBook(1);
  private static final Book NEW_BOOK2 = createBook(2);

  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private BookService bookService;

  private static Book createBook(int id) {
    return Book.builder()
        .count(id * 2)
        .title(Book.Fields.title + "_" + id)
        .author(Book.Fields.author + "_" + id)
        .pageCount(id * 3)
        .isbn(Book.Fields.isbn + "_" + id)
        .publishYear(2000)
        .status(BookStatus.AVALIABLE)
        .build();
  }

  /**
   * cleanUp.
   */
  @BeforeEach
  public void cleanUp() {
    Mono<Void> deleteAll = bookRepository.deleteAll();
    StepVerifier
        .create(deleteAll)
        .verifyComplete();
  }

  @Test
  void insert() {
    getWebTestClient().post()
        .uri(BOOK_URL + INSERT_URL)
        .contentType(APPLICATION_JSON)
        .body(BodyInserters.fromValue(NEW_BOOK1))
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Book.class)
        .value(bookResponse -> {
          assertNotNull(bookResponse.getId());
          assertEquals(2, bookResponse.getCount());
          assertEquals(Book.Fields.title + "_1", bookResponse.getTitle());
          assertEquals(Book.Fields.author + "_1", bookResponse.getAuthor());
          assertEquals(3, bookResponse.getPageCount());
          assertEquals(Book.Fields.isbn + "_1", bookResponse.getIsbn());
        });
  }

  @Test
  void save() {
    Book existBook = bookService.insert(NEW_BOOK1)
        .block();
    existBook.setAuthor("Test Author");
    existBook.setCount(5);

    getWebTestClient().put()
        .uri(BOOK_URL + SAVE_URL)
        .contentType(APPLICATION_JSON)
        .body(BodyInserters.fromValue(existBook))
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Book.class)
        .value(bookResponse -> {
          assertNotNull(bookResponse.getId());
          assertEquals(5, bookResponse.getCount());
          assertEquals("Test Author", bookResponse.getAuthor());
        });
  }

  @Test
  void findAll() {
    bookService.insert(NEW_BOOK1)
        .block();

    getWebTestClient().get()
        .uri(uriBuilder -> uriBuilder.path(BOOK_URL + FINDALL_URL)
            .queryParam(LIMIT, 5)
            .queryParam(OFFSET, 0)
            .build())
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Book.class)
        .value(bookResponse -> assertEquals(1, bookResponse.size()));

    bookService.insert(NEW_BOOK2)
        .block();

    getWebTestClient().get()
        .uri(uriBuilder -> uriBuilder.path(BOOK_URL + FINDALL_URL)
            .queryParam(LIMIT, 5)
            .queryParam(OFFSET, 0)
            .build())
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Book.class)
        .value(bookResponse -> assertEquals(2, bookResponse.size()));
  }

  @Test
  void findById() {
    String bookId = bookService.insert(NEW_BOOK1)
        .block()
        .getId();

    getWebTestClient().get()
        .uri(uriBuilder -> uriBuilder.path(BOOK_URL + FINDBYID_URL)
            .build(bookId))
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Book.class)
        .value(bookResponse -> {
          assertEquals(2, bookResponse.getCount());
          assertEquals(bookId, bookResponse.getId());
          assertEquals(Book.Fields.title + "_1", bookResponse.getTitle());
          assertEquals(Book.Fields.author + "_1", bookResponse.getAuthor());
          assertEquals(3, bookResponse.getPageCount());
          assertEquals(Book.Fields.isbn + "_1", bookResponse.getIsbn());
        });
  }

  @Test
  void count() {
    getWebTestClient().get()
        .uri(BOOK_URL + COUNT_URL)
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Long.class)
        .value(count -> assertEquals(0, count));

    bookService.insert(NEW_BOOK1)
        .block();

    getWebTestClient().get()
        .uri(BOOK_URL + COUNT_URL)
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Long.class)
        .value(count -> assertEquals(1, count));
  }

  @Test
  void deleteById() {
    String bookId = bookService.insert(NEW_BOOK1)
        .block()
        .getId();

    getWebTestClient().delete()
        .uri(uriBuilder -> uriBuilder.path(BOOK_URL + DELETEBYID_URL)
            .build(bookId))
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk();

    getWebTestClient().get()
        .uri(BOOK_URL + COUNT_URL)
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Long.class)
        .value(count -> assertEquals(0, count));
  }
}