package com.mlab.rideshare.service.email;



import com.mlab.rideshare.model.dto.EmailDto;

import java.util.concurrent.CompletableFuture;

public interface EmailService {
    CompletableFuture<EmailDto> send(EmailDto emailDto) throws InterruptedException;
    void sendEmail(EmailDto emailDto);
}
