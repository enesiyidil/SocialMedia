package org.socialmedia.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.socialmedia.dto.request.UserSaveRequestDto;
import org.socialmedia.dto.response.LoginResponseDto;
import org.socialmedia.dto.response.RegisterResponseDto;
import org.socialmedia.entity.Auth;
import org.socialmedia.rabbitmq.model.RegisterModel;
import org.socialmedia.rabbitmq.model.SendMailModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthMapper {

    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    LoginResponseDto authToLoginResponseDto(Auth auth);

    @Mapping(source = "id", target = "authId")
    UserSaveRequestDto toUserSaveRequestDto(Auth auth);

    @Mapping(source = "id", target = "authId")
    RegisterModel toUserRegisterModel(Auth auth);

    RegisterResponseDto toRegisterResponseDto(Auth auth);

    SendMailModel toSendMailModel(Auth auth);
}
