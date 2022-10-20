package com.mlab.rideshare.service.email;

import com.mlab.rideshare.model.dto.EmailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService{
    @Override
    @Async("emailExecutor")
    public CompletableFuture<EmailDto> send(EmailDto emailDto) throws InterruptedException {
        log.info("Sending email to {}",emailDto.getEmail());
        return CompletableFuture.completedFuture(emailDto);
    }

    @Override
    public void sendEmail(EmailDto emailDto) {
        try {
            send(emailDto).thenAcceptAsync(e -> log.info("Mail sent Successful to {}", e.getEmail()));
            log.info("Email sending in progress");
        } catch (InterruptedException e) {
            log.error("Exception occurred while sending email", e );
        }
    }
}
