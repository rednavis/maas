package com.rednavis.data.mapper;

import com.rednavis.data.entity.UserEntity;
import com.rednavis.shared.dto.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper extends GlobaMapper<UserEntity, User> {

}