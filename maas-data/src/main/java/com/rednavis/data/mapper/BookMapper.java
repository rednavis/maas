package com.rednavis.data.mapper;

import java.util.List;
import com.rednavis.data.entity.BookEntity;
import com.rednavis.shared.dto.Book;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {

  BookEntity dtoToEntity(Book book);

  Book entityToDto(BookEntity bookEntity);

  List<BookEntity> listDtoToListEntity(List<Book> bookList);

  List<Book> listEntityToListDto(List<BookEntity> bookEntityList);
}