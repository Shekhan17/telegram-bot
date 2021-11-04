package com.telegram.bot.chatbot.service.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public abstract class Command extends BotCommand {

    Command(String identifier, String description) {
        super(identifier, description);
    }

    protected <Method extends BotApiMethod<Message>> void sendAnswer(AbsSender absSender, Method message) {
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            log.error(String.format("Ошибка %s", e.getMessage()));
            e.printStackTrace();
        }
    }

}