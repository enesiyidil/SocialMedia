package org.socialmedia.service;

import org.socialmedia.dto.request.ActiveStatusRequestDto;
import org.socialmedia.dto.request.LoginRequestDto;
import org.socialmedia.dto.request.RegisterRequestDto;
import org.socialmedia.dto.request.UpdateRequestDto;
import org.socialmedia.dto.response.RegisterResponseDto;
import org.socialmedia.entity.Auth;
import org.socialmedia.entity.enums.EStatus;
import org.socialmedia.exception.AuthServiceException;
import org.socialmedia.exception.ErrorType;
import org.socialmedia.manager.IUserManager;
import org.socialmedia.mapper.AuthMapper;
import org.socialmedia.rabbitmq.producer.ActiveStatusProducer;
import org.socialmedia.rabbitmq.producer.RegisterProducer;
import org.socialmedia.rabbitmq.producer.SendMailProducer;
import org.socialmedia.repository.AuthRepository;
import org.socialmedia.utility.CodeGenerator;
import org.socialmedia.utility.JWTTokenManager;
import org.socialmedia.utility.ServiceManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth,Long> {

    private final AuthRepository authRepository;

    private final JWTTokenManager jwtTokenManager;

    private final IUserManager userManager;

    private final RegisterProducer registerProducer;

    private final ActiveStatusProducer activeStatusProducer;

    private final SendMailProducer sendMailProducer;

    public AuthService(AuthRepository authRepository, JWTTokenManager jwtTokenManager, IUserManager userManager,
                       RegisterProducer registerProducer, ActiveStatusProducer activeStatusProducer,
                       SendMailProducer sendMailProducer) {
        super(authRepository);
        this.authRepository = authRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.userManager = userManager;
        this.registerProducer = registerProducer;
        this.activeStatusProducer = activeStatusProducer;
        this.sendMailProducer = sendMailProducer;
    }

    @Transactional
    public RegisterResponseDto register(RegisterRequestDto request) {

        if(authRepository.existsByEmail(request.getEmail())){
            throw new AuthServiceException(ErrorType.EMAIL_EXITS);
        }
        Auth auth = Auth.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .username(request.getUsername())
                .activationCode(CodeGenerator.generateCode())
                .build();
        authRepository.save(auth);
//        userManager.createNewUser(AuthMapper.INSTANCE.toUserSaveRequestDto(auth));
        registerProducer.sendNewUser(AuthMapper.INSTANCE.toUserRegisterModel(auth));
        sendMailProducer.sendNewUser(AuthMapper.INSTANCE.toSendMailModel(auth));
        return AuthMapper.INSTANCE.toRegisterResponseDto(auth);
    }

    public String login(LoginRequestDto dto) {
        Optional<Auth> optionalAuth = authRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());
        if (optionalAuth.isPresent()){
            if (optionalAuth.get().getStatus().equals(EStatus.ACTIVE)){
                Optional<String> token = jwtTokenManager.createToken(optionalAuth.get().getId(), optionalAuth.get().getRole());
                if (token.isEmpty())
                    throw new AuthServiceException(ErrorType.TOKEN_NOT_CREATE);
                return token.get();
            }
            throw new AuthServiceException(ErrorType.USER_NOT_ACTIVE);
        }
        throw new AuthServiceException(ErrorType.EMAIL_OR_PASSWORD_NOT_VALID);
    }

    @Transactional
    public String activeStatus(ActiveStatusRequestDto dto) {
        Optional<Auth> optionalAuth = authRepository.findByIdAndActivationCode(dto.getId(), dto.getActivationCode());
        if (optionalAuth.isPresent()){
            if (optionalAuth.get().getStatus().equals(EStatus.PENDING)){
                optionalAuth.get().setStatus(EStatus.ACTIVE);
                authRepository.save(optionalAuth.get());
//                userManager.activeStatus(dto);
                activeStatusProducer.activeStatus(dto.getId());
                return "Aktivasyon başarılı";
            }
            throw new AuthServiceException(ErrorType.STATUS_NOT_SUITABLE, ErrorType.STATUS_NOT_SUITABLE.getMessage() + optionalAuth.get().getStatus().name());
        }
        throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
    }

    @Transactional
    public String softDelete(Long id) {
        Optional<Auth> optionalAuth = authRepository.findById(id);
        if (optionalAuth.isPresent()){
            if (!optionalAuth.get().getStatus().equals(EStatus.DELETED)){
                optionalAuth.get().setStatus(EStatus.DELETED);
                authRepository.save(optionalAuth.get());
                return "Silme başarılı";
            }
            throw new AuthServiceException(ErrorType.STATUS_NOT_SUITABLE, ErrorType.STATUS_NOT_SUITABLE.getMessage() + optionalAuth.get().getStatus().name());
        }
        throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
    }

    public String updateEmail(UpdateRequestDto dto) {
        Optional<Auth> optionalAuth = authRepository.findById(dto.getId());
        if (optionalAuth.isEmpty()){
            throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
        }
        if (authRepository.existsByEmail(dto.getEmail())){
            throw new AuthServiceException(ErrorType.EMAIL_EXITS);
        }
        optionalAuth.get().setEmail(dto.getEmail());
        save(optionalAuth.get());
        return "Güncelleme başarılı";
    }
}
