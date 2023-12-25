package org.socialmedia.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.socialmedia.dto.request.UserSaveRequestDto;
import org.socialmedia.entity.UserProfile;
import org.socialmedia.rabbitmq.model.RegisterElasticModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {

    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    UserProfile toUserProfile(UserSaveRequestDto dto);

    RegisterElasticModel toRegisterModel(UserProfile userProfile);
}
