package com.ravi.semester.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendEmail(String toEmail, String subject, String htmlBody) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("ps.project.pic@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);

            // Set HTML content
            helper.setText(htmlBody, true);  // Important: true indicates HTML content

            javaMailSender.send(message);
            System.out.println("Mail Sent...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
