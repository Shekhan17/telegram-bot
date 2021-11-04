package com.telegram.bot.chatbot.template;

import com.telegram.bot.chatbot.service.analyze.MessageParser;
import com.telegram.bot.chatbot.service.command.MessagesStateCommand;
import com.telegram.bot.chatbot.utils.NameBuilder;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingCommandBot {
    @Value("${telegram.bot.name}")
    private String name;
    @Value("${telegram.bot.token}")
    private String token;

    private final MessagesStateCommand messagesStateCommand;
    private final MessageParser messageParser;

    @Override
    public void processNonCommandUpdate(Update update) {
        messageParser.analyze(update.getMessage());
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @PostConstruct
    private void init() {
        register(messagesStateCommand);
    }
}
