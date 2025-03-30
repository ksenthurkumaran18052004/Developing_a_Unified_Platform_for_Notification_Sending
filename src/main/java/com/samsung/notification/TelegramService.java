package com.samsung.notification;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import jakarta.annotation.PostConstruct;

@Service
public class TelegramService extends TelegramLongPollingBot {

    private final String BOT_TOKEN = "7808755308:AAFGXW3CDjCNbUf4RpaLfEiFEuFrLys-sbk";
    private final String BOT_USERNAME = "Prismvitbot";

    private final Set<String> subscribers = new HashSet<>();
    private final File subscriberFile = new File("subscribers.txt");

    @SuppressWarnings("deprecation")
    public TelegramService() {
        super(new DefaultBotOptions());
        loadSubscribers();
    }


    @PostConstruct
    public void registerBot() {
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(this);
            System.out.println("✅ Telegram bot registered!");
        } catch (Exception e) {
            System.err.println("❌ Telegram bot registration failed: " + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage()) return;

        Message message = update.getMessage();
        String chatId = String.valueOf(message.getChatId());
        String text = message.getText();

        System.out.println("📩 Received from Telegram: " + text + " from chat ID: " + chatId);

        if ("/start".equalsIgnoreCase(text)) {
            if (subscribers.add(chatId)) {
                saveSubscribers();
            }
            sendText(chatId, "✅ Subscribed to notifications!");
        } else if ("/unsubscribe".equalsIgnoreCase(text)) {
            if (subscribers.remove(chatId)) {
                saveSubscribers();
                sendText(chatId, "❌ Unsubscribed successfully.");
            } else {
                sendText(chatId, "You were not subscribed.");
            }
        } else {
            sendText(chatId, "🤖 Available commands:\n/start\n/unsubscribe");
        }
    }

    public void sendToSubscribers(String msg) {
        for (String chatId : subscribers) {
            sendText(chatId, msg);
        }
    }

    private void sendText(String chatId, String text) {
        try {
            execute(new SendMessage(chatId, text));
            System.out.println("✅ Sent to " + chatId);
        } catch (Exception e) {
            System.err.println("❌ Failed to send to " + chatId + ": " + e.getMessage());
        }
    }

    private void loadSubscribers() {
        if (!subscriberFile.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(subscriberFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                subscribers.add(line.trim());
            }
            System.out.println("📁 Subscribers loaded: " + subscribers.size());
        } catch (IOException e) {
            System.err.println("⚠️ Error loading subscribers: " + e.getMessage());
        }
    }

    private void saveSubscribers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(subscriberFile))) {
            for (String chatId : subscribers) {
                writer.write(chatId);
                writer.newLine();
            }
            System.out.println("💾 Subscribers saved.");
        } catch (IOException e) {
            System.err.println("⚠️ Error saving subscribers: " + e.getMessage());
        }
    }

    @PostConstruct
    public void init() {
        System.out.println("🟡 Registering Telegram Bot...");
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(this);
            System.out.println("🟢 Telegram Bot registered!");
        } catch (Exception e) {
            System.out.println("❌ Telegram bot registration failed: " + e.getMessage());
        }
    }


    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
