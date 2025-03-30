package com.samsung.notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String message) {
        try {
            MimeMessage mime = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mime, true, "UTF-8");

            String name = to.split("@")[0];
            String html = load("templates/email_template.html")
                    .replace("{{ recipient_name }}", name)
                    .replace("{{ subject }}", subject)
                    .replace("{{ message }}", message);

            String text = load("templates/email_template.txt")
                    .replace("{{ recipient_name }}", name)
                    .replace("{{ subject }}", subject)
                    .replace("{{ message }}", message);

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, html);
            helper.addInline("logo_cid", new ClassPathResource("static/logo.png"));

            mailSender.send(mime);
            System.out.println("✅ Email sent to: " + to);
        } catch (Exception e) {
            System.out.println("❌ Email error: " + e.getMessage());
        }
    }

    private String load(String path) throws Exception {
        Scanner scanner = new Scanner(new ClassPathResource(path).getInputStream(), StandardCharsets.UTF_8.name());
        return scanner.useDelimiter("\\A").next();
    }
}
