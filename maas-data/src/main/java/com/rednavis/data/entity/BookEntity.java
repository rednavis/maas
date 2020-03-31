package com.rednavis.data.entity;

import com.rednavis.shared.dto.BookStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("book")
@EqualsAndHashCode(callSuper = true)
public class BookEntity extends AbstractEntity {

  @Id
  private String id;
  private int count;
  private String title;
  private String author;
  private int pageCount;
  private String isbn;
  private int publishYear;
  private BookStatus status;
}
