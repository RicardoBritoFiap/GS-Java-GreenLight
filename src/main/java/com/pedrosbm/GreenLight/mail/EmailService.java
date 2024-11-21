package com.pedrosbm.GreenLight.mail;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    JavaMailSender mailSender;

    @RabbitListener(queues = "email-queue")
    public void sendEmail(EmailPayload payload) {
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(payload.email());
        mail.setSubject("GreenLight notification");
        mail.setText(payload.mensagem());

        mailSender.send(mail);
    }

}