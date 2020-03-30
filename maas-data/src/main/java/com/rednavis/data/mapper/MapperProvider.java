package com.rednavis.data.mapper;

import lombok.experimental.UtilityClass;
import org.mapstruct.factory.Mappers;

@UtilityClass
public class MapperProvider {

  public static final BookMapper BOOK_MAPPER = Mappers.getMapper(BookMapper.class);
  public static final UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);
}
