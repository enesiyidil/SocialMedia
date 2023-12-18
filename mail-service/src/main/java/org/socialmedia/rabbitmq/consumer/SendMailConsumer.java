package org.socialmedia.rabbitmq.consumer;

import lombok.RequiredArgsConstructor;
import org.socialmedia.rabbitmq.model.SendMailModel;
import org.socialmedia.service.MailSenderService;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Service
@RequiredArgsConstructor
public class SendMailConsumer {

    private final MailSenderService mailSenderService;

    @RabbitListener(queues = "${rabbitmq.sendMail-queue}")
    public void sendMail(SendMailModel model){
        mailSenderService.sendMail(model);
    }
}
