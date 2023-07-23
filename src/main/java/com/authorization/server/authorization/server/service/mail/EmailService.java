package com.authorization.server.authorization.server.service.mail;

import com.authorization.server.authorization.server.dto.common.EmailOtpDetails;
import jakarta.mail.MessagingException;

public interface EmailService {

    // Method
    // To send a simple email
    String sendSimpleMail(EmailOtpDetails details);


    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailOtpDetails details) throws MessagingException;

}
