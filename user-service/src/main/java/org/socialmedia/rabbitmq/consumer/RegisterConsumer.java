package org.socialmedia.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.socialmedia.entity.UserProfile;
import org.socialmedia.mapper.IUserMapper;
import org.socialmedia.rabbitmq.model.RegisterModel;
import org.socialmedia.rabbitmq.producer.RegisterProducer;
import org.socialmedia.service.UserProfileService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterConsumer {

    private final UserProfileService userProfileService;

    private final RegisterProducer registerProducer;

    @RabbitListener(queues = "${rabbitmq.register-queue}")
    public void createUserFromQueue(RegisterModel model){
        System.out.println(model);
        UserProfile userProfile = userProfileService.save(UserProfile.builder()
                .authId(model.getAuthId())
                .username(model.getUsername())
                .email(model.getEmail())
                .build());

        registerProducer.sendNewUser(IUserMapper.INSTANCE.toRegisterModel(userProfile));
    }
}
