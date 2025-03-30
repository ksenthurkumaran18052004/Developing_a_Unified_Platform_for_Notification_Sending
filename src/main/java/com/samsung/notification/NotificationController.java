package com.samsung.notification;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send-notification/v1")
public class NotificationController {

    @Autowired EmailService emailService;
    @Autowired SMSService smsService;
    @Autowired TelegramService telegramService;

    @PostMapping
    public String send(@RequestBody Map<String, Object> body) {
        String service = (String) body.get("service");
        String message = (String) body.get("message");
        String subject = (String) body.getOrDefault("subject", "");
        List<String> recipients = (List<String>) body.getOrDefault("recipients", List.of());

        switch (service) {
            case "email":
                recipients.forEach(r -> emailService.sendEmail(r, subject, message));
                break;
            case "twilio":
                recipients.forEach(r -> smsService.sendSMS(r, message));
                break;
            case "telegram":
                telegramService.sendToSubscribers(message);
                break;
            default:
                return "❌ Invalid service";
        }

        return "✅ Notification sent!";
    }
}
