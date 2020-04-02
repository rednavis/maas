package com.rednavis.data.mapper;

import com.rednavis.data.entity.BookEntity;
import com.rednavis.shared.dto.Book;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper extends GlobaMapper<BookEntity, Book> {

}