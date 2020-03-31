package com.rednavis.mock.service;

import java.util.ArrayList;
import com.rednavis.shared.dto.Book;
import com.rednavis.shared.dto.BookStatus;
import com.rednavis.webflux.service.MaasDataBookRestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.andreinc.mockneat.MockNeat;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookMockService {

  private final MaasDataBookRestService maasDataBookRestService;

  /**
   * mock.
   */
  public void mock() {
    maasDataBookRestService.deleteAll()
        .thenMany(generateMockBooks())
        .flatMap(mockBook -> maasDataBookRestService.insert(mockBook))
        .subscribe();
  }

  private Flux<Book> generateMockBooks() {
    MockNeat mock = MockNeat.threadLocal();
    return Flux.fromIterable(mock.filler(() -> new Book())
        .setter(Book::setCount, mock.intSeq())
        .setter(Book::setTitle, mock.countries().names())
        .setter(Book::setAuthor, mock.users())
        .setter(Book::setPageCount, mock.ints().range(100, 500))
        .setter(Book::setIsbn, mock.creditCards().names())
        .setter(Book::setPublishYear, mock.localDates().map(localDate -> localDate.getYear()))
        .setter(Book::setStatus, mock.from(BookStatus.class))
        .list(() -> new ArrayList<>(), 100)
        .val());
  }
}
