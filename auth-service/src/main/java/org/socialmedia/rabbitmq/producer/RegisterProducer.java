package org.socialmedia.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.socialmedia.rabbitmq.model.RegisterModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.auth-exchange}")
    private String exchange;

    @Value("${rabbitmq.register-bindingKey}")
    private String registerBindingKey;

    public void sendNewUser(RegisterModel model){
        rabbitTemplate.convertAndSend(exchange, registerBindingKey, model);
    }
}
