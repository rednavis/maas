package com.rednavis.data.mapper;

import com.rednavis.data.entity.RefreshTokenEntity;
import com.rednavis.shared.dto.RefreshToken;
import org.mapstruct.Mapper;

@Mapper
public interface RefreshTokenMapper extends GlobaMapper<RefreshTokenEntity, RefreshToken> {

}