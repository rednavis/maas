package com.rednavis.bpm.delegate;

import static com.rednavis.shared.util.RestUrlUtils.BOOK_URL;
import static com.rednavis.shared.util.RestUrlUtils.FINDBYID_URL;

import java.util.Map;
import com.rednavis.shared.dto.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Slf4j
@Component
@RequiredArgsConstructor
public class DemoDelegate implements JavaDelegate {

  private final RestTemplate restTemplate;

  @Override
  public void execute(DelegateExecution execution) {
    Map<String, Object> variables = execution.getVariables();
    for (String key : variables.keySet()) {
      if (key.equals("bookId")) {
        String bookId = (String) variables.get(key);
        Book book = findById(bookId);
        execution.setVariable("book", book.toString());
      }
    }
  }

  private Book findById(String id) {
    String url = new DefaultUriBuilderFactory().builder()
        .path("http://maas-data")
        .path(BOOK_URL)
        .path(FINDBYID_URL)
        .build(id)
        .toString();
    ResponseEntity<Book> restExchange = restTemplate.exchange(url, HttpMethod.GET, null, Book.class);
    return restExchange.getBody();
  }
}
