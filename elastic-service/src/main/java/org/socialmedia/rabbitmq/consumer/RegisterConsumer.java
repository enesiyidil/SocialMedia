package org.socialmedia.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.socialmedia.mapper.IUserMapper;
import org.socialmedia.rabbitmq.model.RegisterElasticModel;
import org.socialmedia.service.UserProfileService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterConsumer {

    private final UserProfileService userProfileService;

    @RabbitListener(queues = "${rabbitmq.elastic-queue}")
    public void createUserFromQueue(RegisterElasticModel model){
        System.out.println(model);
        userProfileService.save(IUserMapper.INSTANCE.toUserProfile(model));
    }
}
