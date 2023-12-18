package org.socialmedia.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.socialmedia.rabbitmq.model.RegisterModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActiveStatusProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.auth-exchange}")
    private String exchange;

    @Value("${rabbitmq.activeStatus-bindingKey}")
    private String activeStatusBindingKey;

    public void activeStatus(Long id){
        rabbitTemplate.convertAndSend(exchange, activeStatusBindingKey, id);
    }
}
