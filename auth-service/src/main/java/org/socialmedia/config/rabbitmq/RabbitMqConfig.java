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

    @Value("${rabbitmq.sendMail-queue}")
    private String sendMailQueueName;

    @Value("${rabbitmq.sendMail-bindingKey}")
    private String sendMailBindingKey;

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
    Queue sendMailQueue(){
        return new Queue(sendMailQueueName);
    }

    @Bean
    public Binding registerBinding(Queue registerQueue, DirectExchange exchangeAuth){
        return BindingBuilder.bind(registerQueue).to(exchangeAuth).with(registerBindingKey);
    }

    @Bean
    public Binding activeStatusBinding(Queue activeStatusQueue, DirectExchange exchangeAuth){
        return BindingBuilder.bind(activeStatusQueue).to(exchangeAuth).with(activeStatusBindingKey);
    }

    @Bean
    public Binding sendMailBinding(Queue sendMailQueue, DirectExchange exchangeAuth){
        return BindingBuilder.bind(sendMailQueue).to(exchangeAuth).with(sendMailBindingKey);
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
