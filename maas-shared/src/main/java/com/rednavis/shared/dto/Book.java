package com.rednavis.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class Book {

  private String id;
  private int count;
  private String title;
  private String author;
  private int pageCount;
  private String isbn;
  private int publishYear;
  private BookStatus status;
}
