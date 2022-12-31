package com.movieland.service.impl;

import com.movieland.event.MovieEvent;
import com.movieland.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultEmailSenderService implements EmailSenderService {

    @Value("${devEmail}")
    private String sendTo;
    private final JavaMailSender sender;

    @Override
    @KafkaListener(topics = "movieNotification")
    public void handleNotification(MovieEvent movie){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sendTo);
        message.setSubject("Movie add");
        message.setText(movie.getMovie());
        message.setFrom("movieland");

        log.info("Movie event {}", movie.getMovie());
        sender.send(message);
    }
}
