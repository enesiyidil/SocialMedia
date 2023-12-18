package org.socialmedia.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.elastic-exchange}")
    private String exchange;

    @Value("${rabbitmq.elastic-queue}")
    private String elasticQueueName;

    @Value("${rabbitmq.elastic-bindingKey}")
    private String elasticBindingKey;


    @Bean
    DirectExchange exchangeElastic(){
        return new DirectExchange(exchange);
    }

    @Bean
    Queue elasticQueue(){
        return new Queue(elasticQueueName);
    }

    @Bean
    public Binding elasticBinding(Queue elasticQueue, DirectExchange exchangeElastic){
        return BindingBuilder.bind(elasticQueue).to(exchangeElastic).with(elasticBindingKey);
    }

    @Bean
    MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
