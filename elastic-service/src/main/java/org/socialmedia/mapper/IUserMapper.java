package org.socialmedia.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.socialmedia.dto.response.UserProfileResponseDto;
import org.socialmedia.entity.UserProfile;
import org.socialmedia.rabbitmq.model.RegisterElasticModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {

    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

//    @Mapping(source = "id", target = "userProfileId")
    UserProfile toUserProfile(UserProfileResponseDto dto);

//    @Mapping(source = "id",target = "userProfileId")
    UserProfile toUserProfile(RegisterElasticModel model);
}
