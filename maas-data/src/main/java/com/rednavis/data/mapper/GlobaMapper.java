package com.rednavis.data.mapper;

import java.util.List;
import com.rednavis.data.entity.AbstractEntity;

public interface GlobaMapper<E extends AbstractEntity, D> {

  E dtoToEntity(D dto);

  D entityToDto(E entity);

  List<E> listDtoToListEntity(List<D> dtoList);

  List<D> listEntityToListDto(List<E> entityList);
}
