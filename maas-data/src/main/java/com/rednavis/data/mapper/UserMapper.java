package com.rednavis.data.mapper;

import java.util.List;
import com.rednavis.data.entity.UserEntity;
import com.rednavis.shared.dto.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

  UserEntity dtoToEntity(User user);

  User entityToDto(UserEntity userEntity);

  List<UserEntity> listDtoToListEntity(List<User> userList);

  List<User> listEntityToListDto(List<UserEntity> userEntityList);
}
