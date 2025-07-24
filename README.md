Samsung R&D Prism Project - 📬 Unified Platform for Sending Notification – using Spring Boot

A centralized service for sending notifications via Email, SMS (Twilio), and Telegram Bot, built with Spring Boot.

✨ Features
✅ Send HTML Emails using SMTP (Gmail or Private Email via vitprism.online)
📩 Send SMS using Twilio Messaging Service
🤖 Send Telegram messages to subscribed users
📂 Automatically maintains subscribers.txt file for Telegram users
🐳 Docker and Maven compatible


🚀 How to Run

1️⃣ Prerequisites

Java 17+

Maven

Docker (for SMTP if using Postfix)

Telegram Bot (created via @BotFather)

Twilio credentials


2️⃣ Environment Configuration

Update your application.properties:


# Email Configuration
spring.mail.host=mail.privateemail.com
spring.mail.port=587
spring.mail.username=senthur@vitprism.online
spring.mail.password=Senthur@04
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.default-encoding=UTF-8
spring.mail.properties.mail.smtp.ssl.trust=mail.privateemail.com

# Twilio
twilio.account.sid=AC0066b66eb10e20fa93e18ca23f825f7b
twilio.auth.token=f69dc226a491a1c8765e5295ff2e47e4
twilio.messaging.sid=MG4f015d906a8b46bddefd56bdf3a0a38e

# Telegram
telegram.bot.token=7808755308:AAFGXW3CDjCNbUf4RpaLfEiFEuFrLys-sbk
telegram.bot.username=Prismvitbot


3️⃣ Build & Run

# Build the project
mvn clean install

# Run the Spring Boot App
mvn spring-boot:run

App will be available at http://localhost:8080

📬 API Endpoint

POST /send-notification/v1

📧 For Email

{
  "service": "email",
  "recipients": ["senthur@vitprism.online"],
  "subject": "Spring Boot Email",
  "message": "Hello from Spring Boot!"
}


📱 For SMS

{
  "service": "twilio",
  "recipients": ["+919XXXXXXXXX"],
  "message": "Your OTP is 123456"
}


🤖 For Telegram

{
  "service": "telegram",
  "message": "System Maintenance at 2 AM"
}


🧠 Telegram Bot Instructions

Talk to your bot at t.me/Prismvitbot

Send /start to subscribe

Send /unsubscribe to stop notifications

Subscribers saved automatically in subscribers.txt


-> Status

✅ Email working (Gmail + Private Email SMTP)

✅ Twilio SMS integrated and tested

✅ Telegram messages working with bot + subscribers

✅ Deployed locally & ready for production

