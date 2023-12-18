package org.socialmedia.service;

import lombok.RequiredArgsConstructor;
import org.socialmedia.rabbitmq.model.SendMailModel;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final JavaMailSender javaMailSender;

    public void sendMail(SendMailModel model) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("enesiyidil16@gmail.com");
        message.setTo(model.getEmail());
        message.setSubject("Hesabı aktif et");
        message.setText("Code: " + model.getActivationCode());

        javaMailSender.send(message);
    }
}
