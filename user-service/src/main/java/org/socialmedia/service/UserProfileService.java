package org.socialmedia.service;

import org.socialmedia.dto.request.ActiveStatusRequestDto;
import org.socialmedia.dto.request.UpdateAuthRequestDto;
import org.socialmedia.dto.request.UpdateRequestDto;
import org.socialmedia.dto.request.UserSaveRequestDto;
import org.socialmedia.entity.UserProfile;
import org.socialmedia.entity.enums.EStatus;
import org.socialmedia.exception.ErrorType;
import org.socialmedia.exception.UserServiceException;
import org.socialmedia.manager.IAuthManager;
import org.socialmedia.mapper.IUserMapper;
import org.socialmedia.rabbitmq.producer.RegisterProducer;
import org.socialmedia.repository.UserProfileRepository;
import org.socialmedia.utility.JWTTokenManager;
import org.socialmedia.utility.ServiceManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile, Long> {

    private final UserProfileRepository repository;

    private final JWTTokenManager jwtTokenManager;

    private final IAuthManager authManager;

    private final RegisterProducer registerProducer;

    public UserProfileService(UserProfileRepository repository, JWTTokenManager jwtTokenManager, IAuthManager authManager, RegisterProducer registerProducer) {
        super(repository);
        this.repository = repository;
        this.jwtTokenManager = jwtTokenManager;
        this.authManager = authManager;
        this.registerProducer = registerProducer;
    }

    public Boolean createNewUser(UserSaveRequestDto dto) {
        UserProfile userProfile = IUserMapper.INSTANCE.toUserProfile(dto);
        save(userProfile);
        registerProducer.sendNewUser(IUserMapper.INSTANCE.toRegisterModel(userProfile));
        return true;
    }

    public Boolean activeStatus(ActiveStatusRequestDto dto) {
        Optional<UserProfile> optionalUserProfile = repository.findOptionalByAuthId(dto.getId());
        if (optionalUserProfile.isEmpty()){
            throw new UserServiceException(ErrorType.USER_NOT_FOUND);
        }
        optionalUserProfile.get().setStatus(EStatus.ACTIVE);
        repository.save(optionalUserProfile.get());
        return true;
    }

    public Boolean softDelete(Long id) {
        Optional<UserProfile> optionalUserProfile = repository.findOptionalByAuthId(id);
        if (optionalUserProfile.isEmpty()){
            throw new UserServiceException(ErrorType.USER_NOT_FOUND);
        }
        if (!optionalUserProfile.get().getStatus().equals(EStatus.DELETED)){
            optionalUserProfile.get().setStatus(EStatus.DELETED);
            repository.save(optionalUserProfile.get());
            return true;
        }
        throw new UserServiceException(ErrorType.STATUS_NOT_SUITABLE, ErrorType.STATUS_NOT_SUITABLE.getMessage() + optionalUserProfile.get().getStatus().name());
    }

    @Transactional
    public String updateProfile(UpdateRequestDto dto) {
        Optional<Long> authId = jwtTokenManager.getIdFromToken(dto.getToken());
        if (authId.isEmpty()){
            throw new UserServiceException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> optionalUserProfile = repository.findOptionalByAuthId(authId.get());
        if (optionalUserProfile.isEmpty()){
            throw new UserServiceException(ErrorType.USER_NOT_FOUND);
        }
        UserProfile userProfile = optionalUserProfile.get();
        if (!userProfile.getEmail().equals(dto.getEmail())){
            userProfile.setEmail(dto.getEmail());
            authManager.updateEmail(UpdateAuthRequestDto.builder()
                    .id(userProfile.getAuthId())
                    .email(userProfile.getEmail())
                    .build());
        }
        userProfile.setPhone(dto.getPhone() == null ? userProfile.getPhone() : dto.getPhone());
        userProfile.setAvatar(dto.getAvatar() == null ? userProfile.getAvatar() : dto.getAvatar());
        userProfile.setAddress(dto.getAddress() == null ? userProfile.getAddress() : dto.getAddress());
        userProfile.setAbout(dto.getAbout() == null ? userProfile.getAbout() : dto.getAbout());
        save(userProfile);
        return "Profil başarıyla güncellendi";
    }

    public Long getUserIdfromToken(String token) {
        Long authId = jwtTokenManager.getIdFromToken(token).orElseThrow(() -> new UserServiceException(ErrorType.INVALID_TOKEN));

        Optional<UserProfile> optionalUserProfile = repository.findByAuthId(authId);
        UserProfile userProfile = optionalUserProfile.orElseThrow(() -> new UserServiceException(ErrorType.USER_NOT_FOUND));
        return userProfile.getId();
    }

    @Cacheable(value = "userProfile")
    public UserProfile findByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new UserServiceException(ErrorType.USER_NOT_FOUND));
    }
}
