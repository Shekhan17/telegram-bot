package com.telegram.bot.chatbot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageType {
    TEXT("Сообщения"),
    MEDIA("Медиа файлы"),
    STICKER("Стикеры");

    private final String value;
}
