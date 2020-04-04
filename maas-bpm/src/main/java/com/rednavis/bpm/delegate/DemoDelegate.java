package com.rednavis.bpm.delegate;

import java.util.Map;
import java.util.stream.Collectors;
import com.rednavis.shared.dto.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class DemoDelegate implements JavaDelegate {

  private final RestTemplate restTemplate;

  @Override
  public void execute(DelegateExecution execution) {
    Map<String, Object> variables = execution.getVariables();
    log.info("variables: {}", variables.entrySet()
        .stream()
        .map(entry -> entry.getKey() + " - " + entry.getValue())
        .collect(Collectors.joining(", ")));
    boolean exists = variables.containsKey("bookId");
    if (exists) {
      String bookId = (String) variables.get("bookId");
      log.info("bookId: {}", bookId);
      Book book = findById(bookId);
      log.info("Book: {}", book);
      execution.setVariable("book", book.toString());
    }
  }

  private Book findById(String id) {
    String url = "http://maas-data/api/book/findById/" + id;
    log.info("URL: {}", url);
    ResponseEntity<Book> restExchange = restTemplate.exchange(url, HttpMethod.GET, null, Book.class);
    return restExchange.getBody();
  }
}
