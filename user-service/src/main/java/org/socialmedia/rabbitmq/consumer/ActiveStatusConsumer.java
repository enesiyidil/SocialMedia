package org.socialmedia.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.socialmedia.dto.request.ActiveStatusRequestDto;
import org.socialmedia.service.UserProfileService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActiveStatusConsumer {

    private final UserProfileService userProfileService;

    @RabbitListener(queues = "${rabbitmq.activeStatus-queue}")
    public void createUserFromQueue(Long id){
        userProfileService.activeStatus(ActiveStatusRequestDto.builder().id(id).build());
    }
}
