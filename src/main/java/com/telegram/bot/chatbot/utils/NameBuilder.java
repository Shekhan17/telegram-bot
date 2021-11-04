package com.telegram.bot.chatbot.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NameBuilder {

    public static String getUserName(Message msg) {
        return getUserName(msg.getFrom());
    }

    public static String getUserName(User user) {
        return (user.getUserName() != null) ? user.getUserName() :
                String.format("%s %s", Objects.requireNonNullElse(user.getLastName(), ""),
                        Objects.requireNonNullElse(user.getFirstName(), ""));
    }
}
