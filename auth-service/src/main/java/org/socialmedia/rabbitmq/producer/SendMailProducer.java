package org.socialmedia.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.socialmedia.rabbitmq.model.SendMailModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendMailProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.auth-exchange}")
    private String exchange;

    @Value("${rabbitmq.sendMail-bindingKey}")
    private String sendMailBindingKey;

    public void sendNewUser(SendMailModel model){
        rabbitTemplate.convertAndSend(exchange, sendMailBindingKey, model);
    }
}
