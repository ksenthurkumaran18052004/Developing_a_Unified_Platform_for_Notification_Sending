    package com.samsung.notification;

    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Service;

    import com.twilio.Twilio;
    import com.twilio.rest.api.v2010.account.Message;

    @Service
    public class SMSService {

        @Value("${twilio.account.sid}") String accountSid;
        @Value("${twilio.auth.token}") String authToken;
        @Value("${twilio.messaging.sid}") String messagingSid;

        public void sendSMS(String to, String body) {
            try {
                Twilio.init(accountSid, authToken);
                Message msg = Message.creator(
                        new com.twilio.type.PhoneNumber(to),
                        messagingSid,
                        body
                ).create();
                System.out.println("✅ SMS sent: " + msg.getSid());
            } catch (Exception e) {
                System.out.println("❌ SMS error: " + e.getMessage());
            }
        }
    }
