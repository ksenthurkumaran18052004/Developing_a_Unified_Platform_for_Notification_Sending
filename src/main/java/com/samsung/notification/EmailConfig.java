// package com.samsung.notification;

// import java.util.Properties;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.mail.javamail.JavaMailSenderImpl;

// @Configuration
// public class EmailConfig {

//     @Bean
//     public JavaMailSender getJavaMailSender() {
//         JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//         mailSender.setHost("localhost");
//         mailSender.setPort(25);
//         mailSender.setUsername("senthur@vitprism.online");
//         mailSender.setPassword("Senthur@04");

//         Properties props = mailSender.getJavaMailProperties();
//         props.put("mail.transport.protocol", "smtp");
//         props.put("mail.smtp.auth", "false");
//         props.put("mail.smtp.starttls.enable", "false");

//         return mailSender;
//     }
// }
