package com.rednavis.api.mapper;

import com.rednavis.shared.dto.User;
import com.rednavis.shared.security.CurrentUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CurrentUserMapper {

  CurrentUser userToCurrentUser(User user);
}
