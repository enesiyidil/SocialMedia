package org.socialmedia.rabbitmq.producer;

import lombok.RequiredArgsConstructor;
import org.socialmedia.rabbitmq.model.RegisterElasticModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.elastic-exchange}")
    private String exchange;

    @Value("${rabbitmq.elastic-bindingKey}")
    private String elasticBindingKey;

    public void sendNewUser(RegisterElasticModel model){
        rabbitTemplate.convertAndSend(exchange, elasticBindingKey, model);
    }
}
