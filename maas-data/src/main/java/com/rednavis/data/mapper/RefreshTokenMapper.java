package com.rednavis.data.mapper;

import java.util.List;
import com.rednavis.data.entity.RefreshTokenEntity;
import com.rednavis.shared.dto.RefreshToken;
import org.mapstruct.Mapper;

@Mapper
public interface RefreshTokenMapper {

  RefreshTokenEntity dtoToEntity(RefreshToken refreshToken);

  RefreshToken entityToDto(RefreshTokenEntity refreshTokenEntity);

  List<RefreshTokenEntity> listDtoToListEntity(List<RefreshToken> refreshTokenList);

  List<RefreshToken> listEntityToListDto(List<RefreshTokenEntity> refreshTokenEntityList);
}