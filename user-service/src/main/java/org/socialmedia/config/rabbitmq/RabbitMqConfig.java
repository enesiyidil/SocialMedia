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

    @Value("${rabbitmq.auth-exchange}")
    private String exchange;

    @Value("${rabbitmq.register-queue}")
    private String registerQueueName;

    @Value("${rabbitmq.register-bindingKey}")
    private String registerBindingKey;

    @Value("${rabbitmq.activeStatus-queue}")
    private String activeStatusQueueName;

    @Value("${rabbitmq.activeStatus-bindingKey}")
    private String activeStatusBindingKey;

    @Value("${rabbitmq.elastic-exchange}")
    private String elasticExchange;

    @Value("${rabbitmq.elastic-queue}")
    private String elasticQueueName;

    @Value("${rabbitmq.elastic-bindingKey}")
    private String elasticBindingKey;

    @Bean
    DirectExchange exchangeAuth(){
        return new DirectExchange(exchange);
    }

    @Bean
    Queue registerQueue(){
        return new Queue(registerQueueName);
    }

    @Bean
    Queue activeStatusQueue(){
        return new Queue(activeStatusQueueName);
    }

    @Bean
    DirectExchange exchangeElastic(){
        return new DirectExchange(elasticExchange);
    }

    @Bean
    Queue elasticQueue(){
        return new Queue(elasticQueueName);
    }

    @Bean
    public Binding registerBinding(Queue registerQueue, DirectExchange exchangeAuth){
        return BindingBuilder.bind(registerQueue).to(exchangeAuth).with(registerBindingKey);
    }

    @Bean
    public Binding elasticBinding(Queue elasticQueue, DirectExchange exchangeElastic){
        return BindingBuilder.bind(elasticQueue).to(exchangeElastic).with(elasticBindingKey);
    }

    @Bean
    public Binding activeStatusBinding(Queue activeStatusQueue, DirectExchange exchangeAuth){
        return BindingBuilder.bind(activeStatusQueue).to(exchangeAuth).with(activeStatusBindingKey);
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
